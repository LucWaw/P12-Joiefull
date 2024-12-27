package com.openclassrooms.p12_joiefull

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.openclassrooms.p12_joiefull.ui.theme.P12_JoiefullTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            P12_JoiefullTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CardClothes(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun CardClothes(modifier: Modifier = Modifier) {
    val fontSize = 14.sp
    val fontSizeDP: Dp = with(LocalDensity.current) {
        fontSize.toDp() + 3.dp
    }
    Column(modifier = Modifier.width(198.dp)) {
        ImageWithFavoriteButton()
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text("Veste urbaine", fontSize = fontSize, fontWeight = FontWeight.Bold)
                Text("89€", fontSize = fontSize)
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
                    Text("4.3", fontSize = fontSize)
                }

                Text(
                    modifier = Modifier.alpha(0.7f),
                    textDecoration = TextDecoration.LineThrough,
                    text = "120€",
                    fontSize = fontSize
                )

            }
        }
    }

}

@Composable
fun ImageWithFavoriteButton() {
    Box {
        AsyncImage(
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1f)  // This ensures a square aspect ratio
                .clip(RoundedCornerShape(20.dp))
                .align(Alignment.Center),
            model = "https://raw.githubusercontent.com/OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main/img/tops/3.jpg",
            contentDescription = "Placeholder TODO",
        )

        AddToFavorite(modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(12.dp))
    }


}

@Preview
@Composable
fun AddToFavorite(modifier: Modifier = Modifier) {
    val fontSize = 14.sp
    val fontSizeDP: Dp = with(LocalDensity.current) {
        fontSize.toDp() + 3.dp
    }
    Row(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(20.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_favorite_border_24),
            contentDescription = stringResource(
                R.string.add_to_favorite
            ),
            tint = Color.Unspecified,
            modifier = Modifier.size(fontSizeDP)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text("24", fontSize = fontSize)
    }
}
