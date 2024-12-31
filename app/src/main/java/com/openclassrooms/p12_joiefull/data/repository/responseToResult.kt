package com.openclassrooms.p12_joiefull.data.repository

import android.util.Log
import com.openclassrooms.p12_joiefull.domain.util.NetworkError
import com.openclassrooms.p12_joiefull.domain.util.Result
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import retrofit2.Response


inline fun <reified T> responseToResult(
    response: Response<T>
): Result<T, NetworkError> {
    return if (response.isSuccessful) {
        val body = try {
            response.body() ?: throw JsonDataException()
        } catch (e: JsonDataException) {
            Log.d("responseToResult", e.toString())

            return Result.Error(NetworkError.SERIALIZATION)
        } catch (e: JsonEncodingException) {
            Log.d("responseToResult", e.toString())

            return Result.Error(NetworkError.SERIALIZATION)
        }

        Result.Success(body)
    } else {
        when (response.code()) {
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)

        }
    }
}