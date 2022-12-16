package tech.noope.details

import kotlinx.coroutines.flow.Flow
import tech.noope.common.domain.data.GameDataModel
import tech.noope.common.viewmodel.mvi.ViewEvent
import tech.noope.common.viewmodel.mvi.ViewSideEffect
import tech.noope.common.viewmodel.mvi.ViewState

class DetailContract {

    sealed class Event: ViewEvent {
        object Back: Event()
        data class Retry(val gameId: Int): Event()
        data class LoadGame(val gameId: Int): Event()
        data class SaveToFavorite(val game: GameDataModel): Event()
        data class RemoveFromFavorite(val game: GameDataModel): Event()
    }

    data class State(
        val game: GameDataModel,
        val isLoading: Boolean,
        val isError: Boolean,
        val isFavorited: Boolean
    ): ViewState

    sealed class Effect: ViewSideEffect {
        data class OnSaveToFavorite(
            val message: String
        ): Effect()

        data class OnRemoveFromFavorite(
            val message: String
        ): Effect()

        sealed class Navigation: Effect() {
            object Back: Navigation()
        }
    }
}