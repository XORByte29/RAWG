package tech.noope.home

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tech.noope.common.domain.RawgRepository
import tech.noope.common.viewmodel.BaseViewModel
import tech.noope.home.sources.HomeSource
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RawgRepository,
) : BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {

    override fun setInitialState(): HomeContract.State {
        return HomeContract.State(
            games = null,
            isLoading = true,
            isError = false
        )
    }

    override fun handleEvents(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.GotoDetail -> setEffect {
                HomeContract.Effect.Navigation.GotoDetail(event.game)
            }
            is HomeContract.Event.Retry -> getGames()
            is HomeContract.Event.SearchGame -> getGames(event.keyword)
            is HomeContract.Event.GetGames -> getGames()
        }
    }

    private fun getGames(keyword: String = "") {
        viewModelScope.launch {
            setState {
                copy(games = null, isLoading = true)
            }

            try {
                val flowGames = Pager(PagingConfig(20)) {
                    HomeSource(keyword, repository)
                }.flow

                setState {
                    copy(games = flowGames, isLoading = false)
                }
            } catch (e: Exception) {
                setState {
                    copy(isError = true, isLoading = false)
                }
            }
        }
    }
}