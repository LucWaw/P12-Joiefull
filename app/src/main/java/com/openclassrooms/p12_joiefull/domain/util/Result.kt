package com.openclassrooms.p12_joiefull.domain.util

typealias DomainError = Error

sealed interface Result<out D, out E : Error> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : DomainError>(val error: E) : Result<Nothing, E>
    data object Loading : Result<Nothing, Nothing>
}

inline fun <T, E : Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when (this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
        is Result.Loading -> Result.Loading

    }
}

fun <T, E : Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map {  }
}

typealias EmptyResult<E> = Result<Unit, E>
