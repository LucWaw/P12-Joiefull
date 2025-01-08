package com.openclassrooms.p12_joiefull.ui.shared_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.openclassrooms.p12_joiefull.R
import java.util.Locale

@Composable
fun ClothingInformations(
    price: Double,
    name: String,
    originalPrice: Double,
    rating : Double,
    fontSize: TextUnit,
    fontSizeDP: Dp,
    contentPadding: PaddingValues,
    textWidth: Dp,
    maxLines: Int,
    modifier: Modifier = Modifier
) {
    val stringRating = if (rating.isNaN()) stringResource(R.string.no_rating) else String.format(
        Locale.FRANCE,"%.1f", rating)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(contentPadding)
    ) {
        Column {
            Text(
                name,
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(if (rating.isNaN()) textWidth -30.dp else textWidth),
                maxLines = maxLines
            )
            Text("${price}€", fontSize = fontSize)
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(horizontalAlignment = Alignment.End) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.star),
                    contentDescription = stringResource(
                        R.string.average_star
                    ),
                    tint = Color.Unspecified,
                    modifier = Modifier.size(fontSizeDP)
                )
                Text(stringRating, fontSize = fontSize)
            }

            Text(
                modifier = Modifier.alpha(0.7f),
                textDecoration = TextDecoration.LineThrough,
                text = "${originalPrice}€",
                fontSize = fontSize
            )

        }
    }
}

