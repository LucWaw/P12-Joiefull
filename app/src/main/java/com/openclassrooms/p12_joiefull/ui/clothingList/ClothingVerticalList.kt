package com.openclassrooms.p12_joiefull.ui.clothingList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openclassrooms.p12_joiefull.R
import com.openclassrooms.p12_joiefull.domain.Clothing
import com.openclassrooms.p12_joiefull.ui.clothingList.components.ClothingCard


@Composable
fun ClothingVerticalList(clothes: List<Clothing>,    onAction: (ClothingListAction) -> Unit,
                         modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(stringResource(clothes[0].category.toRessourceString()), fontSize = 22.sp, fontWeight = FontWeight.Bold)
        ClothesList(onAction = onAction,clothes)
    }
}

@Composable
fun ClothesList(onAction: (ClothingListAction) -> Unit, clothes: List<Clothing>, modifier: Modifier = Modifier) {
    LazyRow(modifier = modifier) {
        items(clothes) { clothing ->
            ClothingCard(clothing, onClick = { onAction(ClothingListAction.OnCoinClick(clothing)) }, modifier = Modifier.padding(0.dp,8.dp,8.dp, 0.dp))
        }
    }
}



fun String.toRessourceString(): Int{
    return when(this){
        "TOPS" -> R.string.tops
        "BOTTOMS" -> R.string.bottoms
        "SHOES" -> R.string.shoes
        "ACCESSORIES" -> R.string.accessories
        else -> R.string.Clothing
    }
}