package com.openclassrooms.p12_joiefull.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openclassrooms.p12_joiefull.ui.components.CardClothe
import com.openclassrooms.p12_joiefull.ui.theme.P12_JoiefullTheme


@Composable
fun ClothesVerticalList(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text("Hauts", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        ClothesList()
    }
}

@Composable
fun ClothesList(modifier: Modifier = Modifier) {
    LazyRow(modifier = modifier) {
        items(10) {
            CardClothe(modifier = Modifier.padding(8.dp))
        }
    }
}

@Preview
@Composable
fun ClothesVerticalListPreview() {
    P12_JoiefullTheme {
        ClothesVerticalList()
    }
}

