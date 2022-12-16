package tech.noope.details

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Gamepad
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import tech.noope.common.composable.Loader
import tech.noope.common.composable.NetworkError
import tech.noope.common.domain.data.GameDataModel
import tech.noope.common.viewmodel.mvi.SIDE_EFFECTS_KEY

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    gameId: Int,
    state: DetailContract.State,
    effectFlow: Flow<DetailContract.Effect>?,
    onEventSent: (event: DetailContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: DetailContract.Effect.Navigation) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        onEventSent(DetailContract.Event.LoadGame(gameId))

        effectFlow?.onEach { effect ->
            when(effect) {
                is DetailContract.Effect.Navigation.Back -> onNavigationRequested(effect)
                is DetailContract.Effect.OnRemoveFromFavorite -> {
                    showToaster(context, effect.message)
                }
                is DetailContract.Effect.OnSaveToFavorite -> {
                    showToaster(context, effect.message)
                }
            }
        }?.collect()
    }

    Scaffold(
        topBar = { TopBar(
            isGameFavorited = state.isFavorited,
            onBackPressed = { onEventSent(DetailContract.Event.Back) },
            onFavoriteMenuClicked = {
                if (state.isFavorited) {
                    onEventSent(DetailContract.Event.RemoveFromFavorite(state.game))
                } else {
                    onEventSent(DetailContract.Event.SaveToFavorite(state.game))
                }
            }
        )}
    ) {
        when {
            state.isLoading -> Loader()
            state.isError -> NetworkError {
                onEventSent(DetailContract.Event.Retry(gameId))
            }
            else -> {
                GameDetail(state.game)
            }
        }
    }

}

private fun showToaster(
    context: Context,
    message: String
) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    isGameFavorited: Boolean,
    onBackPressed: () -> Unit = { },
    onFavoriteMenuClicked: () -> Unit = { },
) {
    val isFavorited = rememberUpdatedState(newValue = isGameFavorited)

    TopAppBar(
        title = { Text(text = "Detail Game") },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = { onFavoriteMenuClicked() }) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = if (isFavorited.value) {
                        Color.Red
                    } else {
                        MaterialTheme.colorScheme.inverseSurface
                    }
                )
            }
        }
    )
}

@Composable
private fun GameDetail(
    game: GameDataModel
) {
    LazyColumn {
        item { 
            GameImage(game = game)
        }

        item { 
            GameInfo(game = game)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun GameImage(
    game: GameDataModel
) {
    GlideImage(
        model = game.image,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(480.dp)
    )
}

@Composable
private fun GameInfo(
    game: GameDataModel
) {
    val publisher = when {
        game.publishers?.isNotEmpty() == true -> {
            game.publishers?.first()?.name
        }
        game.developers?.isNotEmpty() == true -> {
            game.developers?.first()?.name
        }
        else -> null
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        publisher?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
            )
        }

        Spacer(modifier = Modifier.padding(vertical = 2.dp))

        Text(
            text = game.name.toString(),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Black,
        )

        Spacer(modifier = Modifier.padding(vertical = 2.dp))

        Text(
            text = "Release date ${game.released}",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
        )

        Spacer(modifier = Modifier.padding(vertical = 2.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "",
                tint = Color(0xFFF57C00),
                modifier = Modifier.size(16.dp)
            )

            Spacer(modifier = Modifier.padding(horizontal = 2.dp))

            Text(
                text = game.rating.toString(),
                style = MaterialTheme.typography.bodySmall,
            )

            Spacer(modifier = Modifier.padding(horizontal = 4.dp))

            Icon(
                imageVector = Icons.Outlined.Gamepad,
                contentDescription = "",
                modifier = Modifier.size(16.dp)
            )

            Spacer(modifier = Modifier.padding(horizontal = 2.dp))

            Text(
                text = "${game.playtime?.toInt()} Played",
                style = MaterialTheme.typography.bodySmall,
            )
        }

        Spacer(modifier = Modifier.padding(vertical = 16.dp))

        Text(
            text = game.description.toString(),
            Modifier.fillMaxWidth()
        )

    }
}