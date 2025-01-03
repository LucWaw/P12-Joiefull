package com.openclassrooms.p12_joiefull.ui.shared_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openclassrooms.p12_joiefull.R

@Composable
fun AddToFavorite(onClickLike: () -> Unit, likeNumber: Int, isLiked: Boolean = false, modifier: Modifier = Modifier) {
    val fontSize = 14.sp
    val fontSizeDP: Dp = with(LocalDensity.current) {
        fontSize.toDp() + 3.dp
    }
    Row(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(20.dp))
            .padding(8.dp).clickable { onClickLike() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = if (isLiked) painterResource(id = R.drawable.baseline_favorite_24)else painterResource(id = R.drawable.baseline_favorite_border_24),
            contentDescription = stringResource(
                R.string.add_to_favorite
            ),
            tint = Color.Unspecified,
            modifier = Modifier.size(fontSizeDP)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(likeNumber.toString(), fontSize = fontSize)
    }
}