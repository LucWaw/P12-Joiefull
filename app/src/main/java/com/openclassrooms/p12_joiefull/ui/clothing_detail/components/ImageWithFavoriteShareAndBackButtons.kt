package com.openclassrooms.p12_joiefull.ui.clothing_detail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.openclassrooms.p12_joiefull.R
import com.openclassrooms.p12_joiefull.domain.Clothing
import com.openclassrooms.p12_joiefull.ui.shared_components.AddToFavorite


@Composable
fun ImageWithFavoriteShareAndBackButtons(
    picture: Clothing.Picture,
    likeNumber: Int,
    isLiked: Boolean = false,
    onClickLike: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        AsyncImage(
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .align(Alignment.Center)
                .height(431.dp),
            model = picture.url,
            contentDescription = picture.description,
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(
                R.string.back
            ),
            tint = Color.Black,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        )

        Icon(
            painter = painterResource(id = R.drawable.share),
            contentDescription = stringResource(
                R.string.share
            ),
            tint = Color.Black,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        )


        AddToFavorite(
            onClickLike,
            likeNumber,
            isLiked,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )
    }
}

@Preview
@Composable
private fun ImageWithFavoriteShareAndBackButtonsPreview() {
    ImageWithFavoriteShareAndBackButtons(
        picture = Clothing.Picture(
            url = "https://www.example.com/image.jpg",
            description = "A beautiful dress"
        ),
        likeNumber = 5,
        onClickLike = {}
    )
}