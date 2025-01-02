package com.openclassrooms.p12_joiefull.ui.clothingList.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.openclassrooms.p12_joiefull.domain.Clothing
import com.openclassrooms.p12_joiefull.ui.shared_components.AddToFavorite
import com.openclassrooms.p12_joiefull.ui.shared_components.ClothingInformations


@Composable
fun ClothingCard(
    clothing: Clothing, onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val fontSize = 14.sp
    val fontSizeDP: Dp = with(LocalDensity.current) {
        fontSize.toDp() + 3.dp
    }
    Column(modifier = modifier
        .clickable(onClick = onClick)
        .width(200.dp)) {
        ImageWithFavoriteButton(clothing.picture, clothing.likes)
        ClothingInformations(
            clothing.price,
            clothing.name,
            clothing.originalPrice,
            fontSize,
            fontSizeDP,
            contentPadding = PaddingValues(8.dp)
        )
    }
}


@Composable
fun ImageWithFavoriteButton(
    picture: Clothing.Picture,
    likeNumber: Int,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        AsyncImage(
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1f)// This ensures a square aspect ratio
                .clip(RoundedCornerShape(20.dp))
                .align(Alignment.Center),
            model = picture.url,
            contentDescription = picture.description,
        )

        AddToFavorite(
            likeNumber,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp)
        )
    }
}

