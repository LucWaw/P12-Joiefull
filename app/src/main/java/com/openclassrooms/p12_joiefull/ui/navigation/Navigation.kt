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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.openclassrooms.p12_joiefull.domain.util.toString
import com.openclassrooms.p12_joiefull.ui.ObserveAsEvents
import com.openclassrooms.p12_joiefull.ui.clothingList.ClothingListAction
import com.openclassrooms.p12_joiefull.ui.clothingList.ClothingListEvent
import com.openclassrooms.p12_joiefull.ui.clothingList.ClothingListOfLists
import com.openclassrooms.p12_joiefull.ui.clothingList.ClothingViewModel
import com.openclassrooms.p12_joiefull.ui.clothing_detail.DetailScreen
import com.openclassrooms.p12_joiefull.ui.shared_components.LoadingScreen
import kotlinx.coroutines.flow.collect
import kotlinx.serialization.Serializable

@Serializable
data class DeepLinkScreen(val id: Int)

@Serializable
data object HomeScreen

const val DEEPLINK_DOMAIN = "joiefull"


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
    val navController = rememberNavController()
    val navigator = rememberSupportingPaneScaffoldNavigator()
    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }

    // To remove custom getters
    val clothing = clothingState

    // Lock the supporting screen when the detail screen is displayed after a click on a deep link to the detail screen
    var rightScreenLocked = false


    if (state.isLoading) {
        LoadingScreen()
    } else {
        SupportingPaneScaffold(
            directive = navigator.scaffoldDirective,
            value = navigator.scaffoldValue,
            mainPane = {
                AnimatedPane {
                    NavHost(
                        navController = navController,
                        startDestination = HomeScreen
                    ) {
                        composable<HomeScreen> {
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
                            // Unlock the supporting screen when the home screen is displayed
                            rightScreenLocked = false
                        }

                        composable<DeepLinkScreen>(
                            deepLinks = listOf(
                                navDeepLink<DeepLinkScreen>(
                                    basePath = "https://$DEEPLINK_DOMAIN",
                                )
                            )
                        ) { composableRoute ->
                            val id = composableRoute.toRoute<DeepLinkScreen>().id

                            val clothingFromState = state.clothing.flatten().find { it.id == id }


                            if (clothingFromState != null) {
                                DetailScreen(
                                    clothing = clothingFromState,
                                    isBackButtonDisplayed = navigator.scaffoldValue[SupportingPaneScaffoldRole.Main] == PaneAdaptedValue.Hidden,
                                    onAction = {
                                        viewModel.onAction(it)
                                    },
                                    onBackClick = {
                                        navigator.navigateBack()
                                    }
                                )
                                // Lock the supporting screen when the detail screen is displayed after a click on a deep link to the detail screen
                                rightScreenLocked = true
                            }
                        }
                    }

                }
            },
            supportingPane = {
                AnimatedPane {
                    if (clothing != null && !rightScreenLocked) {
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