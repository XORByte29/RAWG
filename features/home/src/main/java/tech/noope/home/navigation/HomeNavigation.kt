package tech.noope.home.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import tech.noope.common.navigation.Routes
import tech.noope.common.domain.data.GameDataModel
import tech.noope.home.HomeContract
import tech.noope.home.HomeScreen
import tech.noope.home.HomeViewModel
import tech.noope.navigation.core.NavigationApi

class HomeNavigation : NavigationApi {
    override val route: String = Routes.HOME

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier,
    ) {
        navGraphBuilder.composable(route) {
            HomeDestination(navController, modifier)
        }
    }
}

@Composable
private fun HomeDestination(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val viewModel = hiltViewModel<HomeViewModel>()

    HomeScreen(
        modifier = modifier,
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            when(navigationEffect) {
                is HomeContract.Effect.Navigation.GotoDetail -> {
                    gotoDetail(navigationEffect.game, navController)
                }
            }
        }
    )
}

private fun gotoDetail(game: GameDataModel, navController: NavHostController) {
    val route = "${Routes.DETAIL}/${game.id}"
    navController.navigate(route)
}