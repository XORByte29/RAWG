package tech.noope.details

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tech.noope.common.domain.RawgRepository
import tech.noope.common.domain.data.GameDataModel
import tech.noope.common.viewmodel.BaseViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: RawgRepository,
) : BaseViewModel<DetailContract.Event, DetailContract.State, DetailContract.Effect>(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.IO

    override fun setInitialState(): DetailContract.State {
        return DetailContract.State(
            game = GameDataModel(),
            isLoading = true,
            isError = false,
            isFavorited = false,
        )
    }

    override fun handleEvents(event: DetailContract.Event) {
        when(event) {
            is DetailContract.Event.Back -> setEffect {
                DetailContract.Effect.Navigation.Back
            }
            is DetailContract.Event.SaveToFavorite -> {
                saveToFavorite(event.game)
            }
            is DetailContract.Event.RemoveFromFavorite -> {
                removeFromFavorite(event.game)
            }
            is DetailContract.Event.Retry -> {
                getGame(event.gameId)
            }
            is DetailContract.Event.LoadGame -> {
                getGame(event.gameId)
            }
        }
    }

    private fun getGame(gameId: Int) {
        viewModelScope.launch(coroutineContext) {
            setState {
                copy(isLoading = true)
            }

            try {
                val response = repository.getGame(gameId)
                val favoriteGame = repository.getFavoriteGame(gameId)
                setState {
                    copy(
                        game = response,
                        isFavorited = response == favoriteGame,
                        isLoading = false,
                        isError = false
                    )
                }
            } catch (e: Exception) {
                val a = e.localizedMessage
                setState {
                    copy(isError = true, isLoading = false)
                }
            }
        }
    }

    private fun saveToFavorite(game: GameDataModel) {
        viewModelScope.launch(coroutineContext) {
            try {
                val isSuccess = repository.saveToFavorite(game)
                setState {
                    copy(
                        isFavorited = isSuccess,
                        isLoading = false,
                        isError = false
                    )
                }
                setEffect {
                    if (isSuccess) {
                        DetailContract.Effect.OnSaveToFavorite(MESSAGE_SUCCESS_SAVE_TO_FAVORITE)
                    } else {
                        DetailContract.Effect.OnSaveToFavorite(MESSAGE_FAILED_SAVE_TO_FAVORITE)
                    }
                }
            } catch (e: Exception) {
                setEffect {
                    DetailContract.Effect.OnSaveToFavorite(MESSAGE_FAILED_SAVE_TO_FAVORITE)
                }
            }
        }
    }

    private fun removeFromFavorite(game: GameDataModel) {
        viewModelScope.launch(coroutineContext) {
            try {
                val isSuccess = repository.removeFromFavorite(game)
                setState {
                    copy(
                        isFavorited = !isSuccess,
                        isLoading = false,
                        isError = false
                    )
                }
                setEffect {
                    if (isSuccess) {
                        DetailContract.Effect.OnRemoveFromFavorite(MESSAGE_SUCCESS_REMOVE_FROM_FAVORITE)
                    } else {
                        DetailContract.Effect.OnRemoveFromFavorite(MESSAGE_FAILED_REMOVE_FROM_FAVORITE)
                    }
                }
            } catch (e: Exception) {
                setEffect {
                    DetailContract.Effect.OnRemoveFromFavorite(MESSAGE_FAILED_REMOVE_FROM_FAVORITE)
                }
            }
        }
    }

    companion object {
        private const val MESSAGE_SUCCESS_SAVE_TO_FAVORITE = "MESSAGE_SUCCESS_SAVE_TO_FAVORITE"
        private const val MESSAGE_FAILED_SAVE_TO_FAVORITE = "MESSAGE_FAILED_SAVE_TO_FAVORITE"
        private const val MESSAGE_SUCCESS_REMOVE_FROM_FAVORITE = "MESSAGE_SUCCESS_REMOVE_FROM_FAVORITE"
        private const val MESSAGE_FAILED_REMOVE_FROM_FAVORITE = "MESSAGE_FAILED_REMOVE_FROM_FAVORITE"
    }
}