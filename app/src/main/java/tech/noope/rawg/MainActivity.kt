package tech.noope.rawg

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import tech.noope.common.navigation.Routes
import tech.noope.common.composable.ui.theme.GreyColor
import tech.noope.common.composable.ui.theme.RAWGTheme
import tech.noope.common.composable.ui.theme.SemiDarkColor
import tech.noope.details.navigation.DetailNavigation
import tech.noope.favorite.navigation.FavoriteNavigation
import tech.noope.home.navigation.HomeNavigation
import tech.noope.navigation.core.AppNavigation

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val navigationItems = listOf(
        NavigationItem(
            name = "Home",
            route = Routes.HOME,
            icon = Icons.Default.Home
        ),
        NavigationItem(
            name = "Favorite",
            route = Routes.FAVORITE,
            icon = Icons.Default.Favorite
        ),
    )

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val bottomBarState = remember { MutableTransitionState(true) }
            val navController = rememberNavController()
            val navBackStackEntry = navController.currentBackStackEntryAsState()

            when(navBackStackEntry.value?.destination?.route) {
                Routes.HOME, Routes.FAVORITE -> {
                    bottomBarState.targetState = true
                }
                else -> {
                    bottomBarState.targetState = false
                }
            }

            RAWGTheme(darkTheme = true) {
                Scaffold(
                    bottomBar = {
                        BottomNavigation(
                            navHostController = navController,
                            items = navigationItems,
                            visibilityState = bottomBarState
                        )
                    }
                ) {
                    AppNavigation.SetRouter(
                        startDestination = Routes.HOME,
                        navController = navController,
                        modifier = Modifier.padding(it),
                        routes = listOf(
                            HomeNavigation(),
                            FavoriteNavigation(),
                            DetailNavigation()
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun BottomNavigation(
    navHostController: NavHostController,
    visibilityState: MutableTransitionState<Boolean>,
    items: List<NavigationItem>
) {
    val selectedItem = navHostController.currentBackStackEntryAsState()

    AnimatedVisibility(
        visibleState = visibilityState,
        enter = slideInVertically { it },
        exit = slideOutVertically { it }
    ) {
        NavigationBar(
            modifier = Modifier.fillMaxWidth(),
            contentColor = MaterialTheme.colorScheme.primary,
            containerColor = SemiDarkColor
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = item.route == selectedItem.value?.destination?.route,
                    onClick = {
                        if (item.route != selectedItem.value?.destination?.route) {
                            navHostController.navigate(item.route)
                        }
                    },
                    label = {
                        Text(
                            text = item.name,
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    icon = {
                        Icon(imageVector = item.icon, contentDescription = null)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = GreyColor,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedTextColor = GreyColor,
                        indicatorColor = GreyColor
                    )
                )
            }
        }
    }
}

data class NavigationItem(
    val name: String,
    val route: String,
    val icon: ImageVector
)