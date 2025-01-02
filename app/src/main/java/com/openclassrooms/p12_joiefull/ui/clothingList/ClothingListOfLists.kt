package com.openclassrooms.p12_joiefull.ui.clothingList

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ClothingListOfLists(
    state: ClothingListState,
    onAction: (ClothingListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(state.clothing) { category ->
            ClothingVerticalList(
                onAction = onAction,
                clothes = category
            )
        }
    }
}

