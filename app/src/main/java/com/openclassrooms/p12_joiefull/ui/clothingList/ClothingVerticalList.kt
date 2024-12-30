package com.openclassrooms.p12_joiefull.ui.clothingList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openclassrooms.p12_joiefull.domain.Clothing
import com.openclassrooms.p12_joiefull.ui.clothingList.components.ClothingCard


@Composable
fun ClothingVerticalList(clothes: List<Clothing>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(clothes[0].category, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        ClothesList(clothes)
    }
}

@Composable
fun ClothesList(clothes: List<Clothing>, modifier: Modifier = Modifier) {
    LazyRow(modifier = modifier) {
        items(clothes) { clothing ->
            ClothingCard(clothing, modifier = Modifier.padding(8.dp))
        }
    }
}



