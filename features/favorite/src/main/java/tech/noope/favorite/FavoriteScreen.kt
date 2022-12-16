package tech.noope.favorite

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import tech.noope.common.composable.GameCard
import tech.noope.common.composable.Loader
import tech.noope.common.composable.NetworkError
import tech.noope.common.domain.data.GameDataModel
import tech.noope.common.viewmodel.mvi.SIDE_EFFECTS_KEY

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    state: FavoriteContract.State,
    effectFlow: Flow<FavoriteContract.Effect>?,
    onEventSent: (event: FavoriteContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: FavoriteContract.Effect.Navigation) -> Unit,
) {

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        onEventSent(FavoriteContract.Event.GetGames)

        effectFlow?.onEach { effect ->
            when (effect) {
                is FavoriteContract.Effect.Navigation.GotoDetail -> onNavigationRequested(effect)
                is FavoriteContract.Effect.Navigation.GotoHome -> onNavigationRequested(effect)
            }
        }?.collect()
    }

    when {
        state.isLoading -> Loader()
        state.isError -> NetworkError {
            onEventSent(FavoriteContract.Event.Retry)
        }
        else -> {
            Column(modifier) {
                Text(
                    text = "Favorite Games",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )

                state.games?.let {
                    GameList(state.games, onEventSent)
                }
            }
        }
    }
}

@Composable
private fun GameList(
    games: List<GameDataModel>,
    onEventSent: (event: FavoriteContract.Event) -> Unit,
) {
    if (games.isEmpty()) {
        EmptyList {
            onEventSent(FavoriteContract.Event.GotoHome)
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {

            itemsIndexed(games) { index, game ->
                GameCard(game = game) { data ->
                    onEventSent(FavoriteContract.Event.GotoDetail(data))
                }
            }
        }
    }
}

@Composable
private fun EmptyList(
    modifier: Modifier = Modifier,
    onRetryClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "No favorite games",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Select the game you want to favorite",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(32.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.secondary
        )

        Button(onClick = { onRetryClick() }) {
            Text(
                text = "Goto Game List",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}