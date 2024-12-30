package com.openclassrooms.p12_joiefull.data.repository

import com.openclassrooms.p12_joiefull.data.response.ResponseApiClothing
import com.openclassrooms.p12_joiefull.domain.ClothingResponseModel
import com.openclassrooms.p12_joiefull.domain.util.NetworkError
import retrofit2.Response
import java.nio.channels.UnresolvedAddressException
import com.openclassrooms.p12_joiefull.domain.util.Result
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

suspend inline fun safeCall(
    execute: () -> Response<ResponseApiClothing>
): Result<List<ClothingResponseModel>, NetworkError> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        return Result.Error(NetworkError.NO_INTERNET)
    } catch (e: JsonDataException) {
        return Result.Error(NetworkError.SERIALIZATION)
    } catch (e: JsonEncodingException) {
        return Result.Error(NetworkError.SERIALIZATION)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return Result.Error(NetworkError.UNKNOWN)
    }

    return responseToResult(response)
}