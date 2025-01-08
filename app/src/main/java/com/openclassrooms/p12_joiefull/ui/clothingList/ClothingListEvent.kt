package com.openclassrooms.p12_joiefull.ui.clothingList

import com.openclassrooms.p12_joiefull.domain.util.PossibleError


sealed interface ClothingListEvent {
    data class Error(val error: PossibleError): ClothingListEvent
}