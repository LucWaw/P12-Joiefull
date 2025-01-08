package com.openclassrooms.p12_joiefull.domain.util

import android.content.Context
import com.openclassrooms.p12_joiefull.R

fun PossibleError.toString(context: Context): String {
    val resId = when(this) {
        PossibleError.REQUEST_TIMEOUT -> R.string.error_request_timeout
        PossibleError.TOO_MANY_REQUESTS -> R.string.error_too_many_requests
        PossibleError.NO_INTERNET -> R.string.error_no_internet
        PossibleError.SERVER_ERROR -> R.string.error_server
        PossibleError.SERIALIZATION -> R.string.error_serialization
        PossibleError.UNKNOWN -> R.string.error_unknown
        PossibleError.RATING_ERROR -> R.string.rating_error
    }
    return context.getString(resId)
}