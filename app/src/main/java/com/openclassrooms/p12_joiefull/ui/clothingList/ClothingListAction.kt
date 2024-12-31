package com.openclassrooms.p12_joiefull.ui.clothingList

import com.openclassrooms.p12_joiefull.domain.Clothing


sealed interface ClothingListAction {
    data class OnCoinClick(val clothing: Clothing): ClothingListAction
}