package com.openclassrooms.p12_joiefull.ui.clothingList

import com.openclassrooms.p12_joiefull.domain.Clothing

data class ClothingListState(
    val isLoading: Boolean = false,
    val clothing: List<List<Clothing>> = emptyList(),
    val selectedClothing: Clothing? = null
)