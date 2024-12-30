package com.openclassrooms.p12_joiefull.data.repository

import com.openclassrooms.p12_joiefull.data.network.ClothingApi
import com.openclassrooms.p12_joiefull.domain.ClothingResponseModel
import com.openclassrooms.p12_joiefull.domain.util.NetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import com.openclassrooms.p12_joiefull.domain.util.Result


class ClothingRepository(private val clothingClient: ClothingApi) {
    fun callClothingApi(): Flow<Result<List<ClothingResponseModel>, NetworkError>> =
        flow {
            emit(Result.Loading)

            val result = safeCall {
                clothingClient.getClothes()
            }

            emit(result)
        }.catch {
            emit(Result.Error(NetworkError.UNKNOWN))
        }.flowOn(Dispatchers.IO) // Exécute le flux dans le contexte IO
}