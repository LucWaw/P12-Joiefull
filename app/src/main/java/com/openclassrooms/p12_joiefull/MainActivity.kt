package com.openclassrooms.p12_joiefull

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.openclassrooms.p12_joiefull.ui.clothingList.ClothesVerticalList
import com.openclassrooms.p12_joiefull.ui.clothingList.ClothingViewModel
import com.openclassrooms.p12_joiefull.ui.theme.P12_JoiefullTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            P12_JoiefullTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel = hiltViewModel<ClothingViewModel>()
                    ClothesVerticalList(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}