package com.openclassrooms.p12_joiefull.data.repository

import com.openclassrooms.p12_joiefull.data.network.ClothingApi
import com.openclassrooms.p12_joiefull.data.response.ResponseApiClothingItem
import com.openclassrooms.p12_joiefull.data.response.toClothing
import com.openclassrooms.p12_joiefull.domain.Clothing
import com.openclassrooms.p12_joiefull.domain.util.NetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import com.openclassrooms.p12_joiefull.domain.util.Result
import com.openclassrooms.p12_joiefull.domain.util.map


class ClothingRepository(private val clothingClient: ClothingApi) {
    fun callClothingApi(): Flow<Result<List<Clothing>, NetworkError>> =
        flow {
            emit(Result.Loading)

            val result = safeCall<List<ResponseApiClothingItem>> {
                clothingClient.getClothes()
            }.map {response ->
                response.map { it.toClothing()}
            }

            emit(result)
        }.catch {
            emit(Result.Error(NetworkError.UNKNOWN))
        }.flowOn(Dispatchers.IO)
}