package tech.noope.navigation.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import tech.noope.navigation.register

object AppNavigation {

    @Composable
    fun SetRouter(
        startDestination: String,
        navController: NavHostController,
        routes: List<NavigationApi>,
        modifier: Modifier = Modifier
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            if (routes.isNotEmpty()) {
                routes.forEach {
                    register(it, navController, modifier)
                }
            }
        }
    }
}