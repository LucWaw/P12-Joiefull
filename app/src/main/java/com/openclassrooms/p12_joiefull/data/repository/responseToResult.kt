package com.openclassrooms.p12_joiefull.data.repository

import com.openclassrooms.p12_joiefull.domain.util.PossibleError
import com.openclassrooms.p12_joiefull.domain.util.Result
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import retrofit2.Response


inline fun <reified T> responseToResult(
    response: Response<T>
): Result<T, PossibleError> {
    return if (response.isSuccessful) {
        val body = try {
            response.body() ?: throw JsonDataException()
        } catch (e: JsonDataException) {
            return Result.Error(PossibleError.SERIALIZATION)
        } catch (e: JsonEncodingException) {
            return Result.Error(PossibleError.SERIALIZATION)
        }

        Result.Success(body)
    } else {
        when (response.code()) {
            408 -> Result.Error(PossibleError.REQUEST_TIMEOUT)
            429 -> Result.Error(PossibleError.TOO_MANY_REQUESTS)
            in 500..599 -> Result.Error(PossibleError.SERVER_ERROR)
            else -> Result.Error(PossibleError.UNKNOWN)

        }
    }
}