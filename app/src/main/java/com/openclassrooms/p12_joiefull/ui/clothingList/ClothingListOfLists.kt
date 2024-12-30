package com.openclassrooms.p12_joiefull.ui.clothingList

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.openclassrooms.p12_joiefull.R
import com.openclassrooms.p12_joiefull.domain.Clothing

@Composable
fun ClothingListOfLists(state: ClothingListState, modifier: Modifier = Modifier) {
    if (state.isLoading) {
        LoadingScreen()
    } else {
        val listOfCategories: MutableList<MutableList<Clothing>> = mutableListOf()
        Log.d("ClothingListOfLists", "state.clothing: ${state.clothing}")
        state.clothing.forEach { clothing ->
            if (listOfCategories.isEmpty()) {
                //if the list is empty, add the first category
                listOfCategories.add(mutableListOf(clothing))
            } else {
                var isCategoryFound = false
                for (category in listOfCategories) {
                    // if the category is already in the list, add the clothing to the category
                    if (category[0].category == clothing.category) { //all the clothing in the same category have the same category name
                        category.add(clothing)
                        isCategoryFound = true
                        break
                    }
                }
                // if the category is not in the list, add the category to the list
                if (!isCategoryFound) {
                    listOfCategories.add(mutableListOf(clothing))
                }
            }
        }
        Log.d("ClothingListOfLists", "listOfCategories: $listOfCategories")
        Column(
            modifier = modifier
        ) {
            for (category in listOfCategories) {
                ClothingVerticalList(category)
            }
        }

    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF99F43)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.joeifull_screen),
            contentDescription = "Loading Screen",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
private fun LoadingScreenPreview() {
    LoadingScreen()
}

