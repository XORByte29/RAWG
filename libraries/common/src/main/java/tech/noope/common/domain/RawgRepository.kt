package tech.noope.common.domain

import tech.noope.common.domain.data.BaseResponse
import tech.noope.common.domain.data.GameDataModel

interface RawgRepository {
    suspend fun getGames(
        page: Int = 0,
        pageSize: Int = 20,
        search: String = ""
    ) : BaseResponse

    suspend fun getGame(
        id: Int
    ) : GameDataModel

    suspend fun getFavoriteGames(): List<GameDataModel>

    suspend fun getFavoriteGame(id: Int): GameDataModel?

    suspend fun saveToFavorite(
        game: GameDataModel
    ): Boolean

    suspend fun removeFromFavorite(
        game: GameDataModel
    ): Boolean
}