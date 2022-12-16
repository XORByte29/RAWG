package tech.noope.favorite

import tech.noope.common.domain.data.GameDataModel
import tech.noope.common.viewmodel.mvi.ViewEvent
import tech.noope.common.viewmodel.mvi.ViewSideEffect
import tech.noope.common.viewmodel.mvi.ViewState

class FavoriteContract {

    sealed class Event: ViewEvent {
        object GetGames: Event()
        object Retry : Event()
        object GotoHome: Event()
        data class GotoDetail(val game: GameDataModel): Event()
    }

    data class State(
        val games: List<GameDataModel>,
        val isLoading: Boolean,
        val isError: Boolean
    ): ViewState

    sealed class Effect: ViewSideEffect {
        sealed class Navigation: Effect() {
            object GotoHome: Navigation()
            data class GotoDetail(val game: GameDataModel): Navigation()
        }
    }
}