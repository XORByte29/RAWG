package tech.noope.common.domain.remote

import tech.noope.common.domain.RawgApi
import tech.noope.common.domain.RawgRepository
import tech.noope.common.domain.data.BaseResponse
import tech.noope.common.domain.data.GameDataModel
import tech.noope.common.domain.database.GameDao
import tech.noope.network.ApiClient

class RawgDataStore(
    private val apiClient: ApiClient<RawgApi>,
    private val gameDao: GameDao,
) : RawgRepository {

    override suspend fun getGames(page: Int, pageSize: Int, search: String): BaseResponse {
        return apiClient.call().getGames(page, pageSize, search)
    }

    override suspend fun getGame(id: Int): GameDataModel {
        return apiClient.call().getGame(id)
    }

    override suspend fun getFavoriteGames(): List<GameDataModel> {
        return gameDao.getGames()
    }

    override suspend fun getFavoriteGame(id: Int):GameDataModel {
        return gameDao.getGame(id)
    }

    override suspend fun saveToFavorite(game: GameDataModel): Boolean {
        return gameDao.saveToFavorite(game) > 0
    }

    override suspend fun removeFromFavorite(game: GameDataModel): Boolean {
        return gameDao.removeFromFavorite(game) > 0
    }
}