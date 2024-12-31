package com.openclassrooms.p12_joiefull.ui.clothingList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.openclassrooms.p12_joiefull.R

@Composable
fun ClothingListOfLists(state: ClothingListState, modifier: Modifier = Modifier) {
    if (state.isLoading) {
        LoadingScreen()
    } else {
        LazyColumn(
            modifier = modifier
        ) {
            items(state.clothing) { category ->
                ClothingVerticalList(clothes = category)
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

