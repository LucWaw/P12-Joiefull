package com.openclassrooms.p12_joiefull.ui.clothingList

import com.openclassrooms.p12_joiefull.domain.Clothing


sealed interface ClothingListAction {
    data class OnClothingClick(val clothing: Clothing): ClothingListAction
    data class OnLikeClick(val clothing: Clothing): ClothingListAction
    data class OnAddReviewClick(val clothing: Clothing, val review : Clothing.Review): ClothingListAction
}