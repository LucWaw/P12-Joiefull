package com.openclassrooms.p12_joiefull.ui.navigation

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.openclassrooms.p12_joiefull.domain.util.toString
import com.openclassrooms.p12_joiefull.ui.ObserveAsEvents
import com.openclassrooms.p12_joiefull.ui.clothingList.ClothingListAction
import com.openclassrooms.p12_joiefull.ui.clothingList.ClothingListEvent
import com.openclassrooms.p12_joiefull.ui.clothingList.ClothingListOfLists
import com.openclassrooms.p12_joiefull.ui.clothingList.ClothingViewModel
import com.openclassrooms.p12_joiefull.ui.clothing_detail.DetailScreen
import com.openclassrooms.p12_joiefull.ui.shared_components.LoadingScreen
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptiveClothingGridDetailPane(
    modifier: Modifier = Modifier,
    viewModel: ClothingViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(0) {
        viewModel.loadClothing().collect()
    }
    val clothingState by viewModel.selectedClothing.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveAsEvents(events = viewModel.events) { event ->
        when (event) {
            is ClothingListEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.toString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    val navigator = rememberSupportingPaneScaffoldNavigator()
    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }
    val clothing = clothingState



    if (state.isLoading) {
        LoadingScreen()
    } else {
        SupportingPaneScaffold(
            directive = navigator.scaffoldDirective,
            value = navigator.scaffoldValue,
            mainPane = {
                AnimatedPane {
                    ClothingListOfLists(
                        state = state,
                        onAction = {
                            viewModel.onAction(it)


                            if (it is ClothingListAction.OnClothingClick) {
                                navigator.navigateTo(
                                    SupportingPaneScaffoldRole.Supporting
                                )
                            }
                        }
                    )
                }
            },
            supportingPane = {
                AnimatedPane {
                    if (clothing != null) {
                        DetailScreen(
                            clothing = clothing,
                            isBackButtonDisplayed = navigator.scaffoldValue[SupportingPaneScaffoldRole.Main] == PaneAdaptedValue.Hidden,
                            onAction = {
                                viewModel.onAction(it)
                            },
                            onBackClick = {
                                navigator.navigateBack()
                            }
                        )
                    }
                }
            },
            modifier = modifier
        )
    }
}