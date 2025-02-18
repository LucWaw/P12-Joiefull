package com.openclassrooms.p12_joiefull.domain.util

enum class PossibleError: Error {
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    SERVER_ERROR,
    SERIALIZATION,
    UNKNOWN,
    RATING_ERROR
}