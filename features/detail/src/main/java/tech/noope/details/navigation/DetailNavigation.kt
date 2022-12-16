package tech.noope.details.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import tech.noope.common.navigation.Routes
import tech.noope.details.DetailContract
import tech.noope.details.DetailScreen
import tech.noope.details.DetailViewModel
import tech.noope.navigation.core.NavigationApi

class DetailNavigation : NavigationApi {
    override val route: String = "${Routes.DETAIL}/{${Routes.Param.PARAM_GAME_ID}}"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier,
    ) {
        navGraphBuilder.composable(
            route = route,
            arguments = listOf(navArgument(name = Routes.Param.PARAM_GAME_ID) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val gameId = requireNotNull(backStackEntry.arguments?.getInt(Routes.Param.PARAM_GAME_ID)) {
                "game id is required as an argument"
            }

            DetailDestination(gameId, navController)
        }
    }
}

@Composable
private fun DetailDestination(
    gameId: Int,
    navController: NavHostController
) {
    val viewModel = hiltViewModel<DetailViewModel>()

    DetailScreen(
        gameId = gameId,
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            when(navigationEffect) {
                is DetailContract.Effect.Navigation.Back -> {
                    navController.popBackStack()
                }
            }
        }
    )
}