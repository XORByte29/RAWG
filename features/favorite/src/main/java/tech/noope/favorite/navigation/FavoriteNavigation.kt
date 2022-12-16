package tech.noope.favorite.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import tech.noope.common.domain.data.GameDataModel
import tech.noope.common.navigation.Routes
import tech.noope.favorite.FavoriteContract
import tech.noope.favorite.FavoriteScreen
import tech.noope.favorite.FavoriteViewModel
import tech.noope.navigation.core.NavigationApi

class FavoriteNavigation : NavigationApi {
    override val route: String = Routes.FAVORITE

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier,
    ) {
        navGraphBuilder.composable(route) {
            FavoriteDestination(navController, modifier)
        }
    }
}

@Composable
private fun FavoriteDestination(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val viewModel = hiltViewModel<FavoriteViewModel>()

    FavoriteScreen(
        modifier = modifier,
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            when(navigationEffect) {
                is FavoriteContract.Effect.Navigation.GotoDetail -> {
                    gotoDetail(navigationEffect.game, navController)
                }
                is FavoriteContract.Effect.Navigation.GotoHome -> {
                    navController.popBackStack(Routes.HOME, false)
                }
            }
        }
    )
}

private fun gotoDetail(game: GameDataModel, navController: NavHostController) {
    val route = "${Routes.DETAIL}/${game.id}"
    navController.navigate(route)
}