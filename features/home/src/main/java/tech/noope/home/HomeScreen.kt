package tech.noope.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import tech.noope.common.composable.Loader
import tech.noope.common.composable.NetworkError
import tech.noope.common.domain.data.GameDataModel
import tech.noope.common.viewmodel.mvi.SIDE_EFFECTS_KEY
import tech.noope.common.composable.GameCard

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeContract.State,
    effectFlow: Flow<HomeContract.Effect>?,
    onEventSent: (event: HomeContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: HomeContract.Effect.Navigation) -> Unit,
) {

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        onEventSent(HomeContract.Event.GetGames)

        effectFlow?.onEach { effect ->
            when (effect) {
                is HomeContract.Effect.Navigation.GotoDetail -> onNavigationRequested(effect)
            }
        }?.collect()
    }

    when {
        state.isLoading -> Loader()
        state.isError -> NetworkError {
            onEventSent(HomeContract.Event.Retry)
        }
        else -> {
            Column(modifier) {
                Text(
                    text = "Games For You",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )

                SearchBar(onEventSent)

                state.games?.let {
                    GameList(it, onEventSent)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar(
    onEventSent: (event: HomeContract.Event) -> Unit,
) {
    val state = remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        TextField(
            value = state.value,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onEventSent(HomeContract.Event.SearchGame(state.value.text))
            }),
            onValueChange = { value ->
                state.value = value
            },
            placeholder = {
                Text(text = "Search game")
            },
            trailingIcon = {
                if (state.value != TextFieldValue("")) {
                    IconButton(
                        onClick = {
                            state.value = TextFieldValue("")
                        }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                } else {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "",
                        modifier = Modifier.size(32.dp)
                    )
                }
            },
            singleLine = true,
            maxLines = 1,
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun GameList(
    games: Flow<PagingData<GameDataModel>>,
    onEventSent: (event: HomeContract.Event) -> Unit,
) {
    val lazyGames = games.collectAsLazyPagingItems()

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {

        items(lazyGames) { game ->
            game?.let {
                GameCard(game = it) { data ->
                    onEventSent(HomeContract.Event.GotoDetail(data))
                }
            }
        }

        lazyGames.apply {
            when {
                loadState.refresh is LoadState.Loading -> item { Loader() }
                loadState.append is LoadState.Loading -> item { LoaderItem() }
                loadState.refresh is LoadState.Error -> {
                    val lazyError = lazyGames.loadState.refresh as LoadState.Error
                    item {
                        NetworkError(message = lazyError.error.localizedMessage.orEmpty()) {
                            retry()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LoaderItem() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}