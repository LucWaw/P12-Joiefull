package com.openclassrooms.p12_joiefull.ui.clothing_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openclassrooms.p12_joiefull.R
import com.openclassrooms.p12_joiefull.domain.Clothing
import com.openclassrooms.p12_joiefull.ui.clothingList.ClothingListAction
import com.openclassrooms.p12_joiefull.ui.clothing_detail.components.ImageWithFavoriteShareAndBackButtons
import com.openclassrooms.p12_joiefull.ui.shared_components.ClothingInformations

@Composable
fun DetailScreen(clothing: Clothing, modifier: Modifier = Modifier, onAction: (ClothingListAction) -> Unit) {

    val fontSize = 18.sp
    val fontSizeDP: Dp = with(LocalDensity.current) {
        fontSize.toDp() + 3.dp
    }

    Column(modifier = modifier
        .padding(16.dp)
        .verticalScroll(rememberScrollState())) {
        ImageWithFavoriteShareAndBackButtons(
            picture = clothing.picture,
            likeNumber = clothing.likes,
            isLiked = clothing.isLiked,
            onClickLike = { onAction(ClothingListAction.OnLikeClick(clothing)) },
        )
        ClothingInformations(
            clothing.price,
            clothing.name,
            clothing.originalPrice,
            fontSize,
            fontSizeDP,
            contentPadding = PaddingValues(top = 24.dp, bottom = 12.dp),
            textWidth = 250.dp,
            maxLines = 2
        )

        Text(text = "${clothing.picture.description}.", fontSize = 14.sp)

        UserImageWithRatingBar()

        val text = stringResource(R.string.share_comment)

        val textState = remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            placeholder = { Text(text = text) },
            value = textState.value,
            onValueChange = { textState.value = it },
            shape = RoundedCornerShape(10.dp),
            minLines = 2,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun UserImageWithRatingBar(modifier: Modifier = Modifier) {
    Row (modifier = modifier.padding(top=24.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ){
        Image(
            painter = painterResource(id = R.drawable.user_picture),
            contentDescription = "User picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(39.dp)
                .clip(CircleShape)                       // clip to the circle shape
        )


        var rating by remember { mutableIntStateOf(0) } //default rating will be 0

        StarRatingBar(
            maxStars = 5,
            rating = rating,
            onRatingChanged = {
                rating = it
            }
        )
    }

}

@Composable
fun StarRatingBar(
    maxStars: Int = 5,
    rating: Int,
    onRatingChanged: (Int) -> Unit
) {
    val density = LocalDensity.current.density
    val starSize = (12f * density).dp

    Row(
        modifier = Modifier.selectableGroup(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        for (i in 1..maxStars) {
            val isSelected = i <= rating
            val icon =
                if (isSelected) painterResource(id = R.drawable.star_rating_filled) else painterResource(
                    id = R.drawable.star_rating_border
                )

            Icon(
                painter = icon,
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .selectable(
                        selected = isSelected,
                        onClick = {
                            onRatingChanged(i)
                        }
                    )
                    .width(starSize)
                    .height(starSize)
            )


        }
    }
}

@Preview
@Composable
fun PreviewDetailScreen() {
    DetailScreen(
        clothing = Clothing(
            id = 1,
            name = "T-shirt",
            price = 20.0,
            originalPrice = 30.0,
            likes = 4,
            category = "TOPS",
            picture = Clothing.Picture(
                url = "https://raw.githubusercontent.com/OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-avec-Jetpack-Compose/refs/heads/main/img/tops/2.jpg",
                description = "Super T-shirt"
            )
        ),
        onAction = {}
    )
}