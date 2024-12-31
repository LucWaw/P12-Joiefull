package com.openclassrooms.p12_joiefull.ui.clothingList

import com.openclassrooms.p12_joiefull.domain.util.NetworkError


sealed interface ClothingListEvent {
    data class Error(val error: NetworkError): ClothingListEvent
}