package com.openclassrooms.p12_joiefull.data.repository

import com.openclassrooms.p12_joiefull.data.response.ResponseApiClothing
import com.openclassrooms.p12_joiefull.domain.ClothingResponseModel
import com.openclassrooms.p12_joiefull.domain.util.NetworkError
import com.squareup.moshi.JsonDataException
import retrofit2.Response
import com.openclassrooms.p12_joiefull.domain.util.Result
import com.squareup.moshi.JsonEncodingException


fun responseToResult(
    response: Response<ResponseApiClothing>
): Result<List<ClothingResponseModel>, NetworkError> {
    return if (response.isSuccessful) {
        val body = try {
            response.body() ?: throw JsonDataException("Réponse avec un corps null")
        } catch (e: JsonDataException) {
            return Result.Error(NetworkError.SERIALIZATION)
        } catch (e: JsonEncodingException) {
            return Result.Error(NetworkError.SERIALIZATION)
        }
        val model = body.toClothingResponseModel()

        Result.Success(model)
    } else {
        when (response.code()) {
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)

        }
    }
}