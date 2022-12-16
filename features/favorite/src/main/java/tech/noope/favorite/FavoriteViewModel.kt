package tech.noope.favorite

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tech.noope.common.domain.RawgRepository
import tech.noope.common.viewmodel.BaseViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: RawgRepository
) : BaseViewModel<FavoriteContract.Event, FavoriteContract.State, FavoriteContract.Effect>() {

    override fun setInitialState(): FavoriteContract.State {
        return FavoriteContract.State(
            games = listOf(),
            isLoading = true,
            isError = false
        )
    }

    override fun handleEvents(event: FavoriteContract.Event) {
        when (event) {
            is FavoriteContract.Event.GotoDetail -> setEffect {
                FavoriteContract.Effect.Navigation.GotoDetail(event.game)
            }
            is FavoriteContract.Event.Retry -> getGames()
            is FavoriteContract.Event.GetGames -> getGames()
            is FavoriteContract.Event.GotoHome -> setEffect {
                FavoriteContract.Effect.Navigation.GotoHome
            }
        }
    }

    private fun getGames() {
        viewModelScope.launch {
            setState {
                copy(isLoading = true)
            }

            try {
                val response = repository.getFavoriteGames()

                setState {
                    copy(
                        games = response,
                        isLoading = false,
                        isError = false,
                    )
                }
            } catch (e: Exception) {
                setState {
                    copy(isError = true, isLoading = false)
                }
            }
        }
    }
}