package com.openclassrooms.p12_joiefull.ui.clothing_detail.components

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.openclassrooms.p12_joiefull.R
import com.openclassrooms.p12_joiefull.domain.Clothing
import com.openclassrooms.p12_joiefull.ui.shared_components.AddToFavorite


@Composable
fun ImageWithFavoriteShareAndBackButtons(
    picture: Clothing.Picture,
    likeNumber: Int,
    isBackButtonDisplayed: Boolean,
    clothingId: Int,
    isLiked: Boolean = false,
    onBackClick: () -> Unit,
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

        if (isBackButtonDisplayed) {
            IconButton(
                onClick = { onBackClick() },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(2.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(
                        R.string.back
                    ),
                    tint = Color.Black
                )
            }
        }
        val showDialog = remember { mutableStateOf(false) }
        if (showDialog.value) {
            OpenShareDialog(id = clothingId,
                showDialog = showDialog.value,
                onDismiss = { showDialog.value = false })
        }


        IconButton(
            onClick = { showDialog.value = true },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(2.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.share),
                contentDescription = stringResource(
                    R.string.share
                ),
                tint = Color.Black
            )
        }


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


@Composable
fun OpenShareDialog(id: Int, showDialog: Boolean, onDismiss: () -> Unit) {
    val shareString = "https://joiefull/$id"
    var comment by remember {
        mutableStateOf("")
    }
    // Open share dialog
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Card(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                Column(
                    Modifier
                        .background(Color.White)
                ) {
                    Text(
                        text = stringResource(R.string.share_with_a_comment),
                        modifier = Modifier.padding(8.dp),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )

                    OutlinedTextField(
                        value = comment,
                        onValueChange = { comment = it }, modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        label = { Text(stringResource(R.string.your_comment)) }
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        TextButton(
                            onClick = onDismiss,
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text(stringResource(R.string.dismiss))
                        }
                        val context = LocalContext.current
                        TextButton(
                            onClick = { onConfirmation("$comment, $shareString ", context) },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text(stringResource(R.string.confirm))
                        }
                    }
                }
            }
        }
    }

}

fun onConfirmation(sharedText: String, context: Context) {
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, sharedText)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)

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
        isBackButtonDisplayed = true,
        onClickLike = {},
        onBackClick = {},
        clothingId = 1
    )
}