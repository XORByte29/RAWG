package tech.noope.home

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import tech.noope.common.domain.data.GameDataModel
import tech.noope.common.viewmodel.mvi.ViewEvent
import tech.noope.common.viewmodel.mvi.ViewSideEffect
import tech.noope.common.viewmodel.mvi.ViewState

class HomeContract {

    sealed class Event: ViewEvent {
        object GetGames: Event()
        object Retry : Event()
        data class SearchGame(val keyword: String): Event()
        data class GotoDetail(val game: GameDataModel): Event()
    }

    data class State(
        val games: Flow<PagingData<GameDataModel>>?,
        val isLoading: Boolean,
        val isError: Boolean
    ): ViewState

    sealed class Effect: ViewSideEffect {
        sealed class Navigation: Effect() {
            data class GotoDetail(val game: GameDataModel): Navigation()
        }
    }
}