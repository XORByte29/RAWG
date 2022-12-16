package tech.noope.common.domain.remote

import kotlinx.coroutines.*
import tech.noope.common.domain.RawgApi
import tech.noope.common.domain.RawgRepository
import tech.noope.common.domain.data.BaseResponse
import tech.noope.common.domain.data.GameDataModel
import tech.noope.common.domain.database.GameDao
import tech.noope.common.domain.database.table.GameDataTable
import tech.noope.network.ApiClient
import kotlin.coroutines.CoroutineContext

class RawgDataStore(
    private val apiClient: ApiClient<RawgApi>,
    private val gameDao: GameDao,
) : RawgRepository, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.IO

    override suspend fun getGames(page: Int, pageSize: Int, search: String): BaseResponse {
        var response: BaseResponse
        withContext(coroutineContext) {
            response = apiClient.call().getGames(page, pageSize, search)
        }

        return response
    }

    override suspend fun getGame(id: Int): GameDataModel {
        var game: GameDataModel
        withContext(coroutineContext) {
            game = apiClient.call().getGame(id)
        }

        return game
    }

    override suspend fun getFavoriteGames(): List<GameDataModel> {
        val games: MutableList<GameDataModel> = mutableListOf()
        withContext(coroutineContext) {
            gameDao.getGames().onEach {
                games.add(it.mapIntoDataModel())
            }
        }

        return games
    }

    override suspend fun getFavoriteGame(id: Int): GameDataModel {
        var game: GameDataModel
        withContext(coroutineContext) {
            game = gameDao.getGame(id).mapIntoDataModel()
        }

        return game
    }

    override suspend fun saveToFavorite(game: GameDataModel): Boolean {
        var isSuccess: Boolean
        withContext(coroutineContext) {
            isSuccess = gameDao.saveToFavorite(game.mapIntoDataTable()) > 0
        }

        return isSuccess
    }

    override suspend fun removeFromFavorite(game: GameDataModel): Boolean {
        var isSuccess: Boolean
        withContext(coroutineContext) {
            isSuccess = gameDao.removeFromFavorite(game.mapIntoDataTable()) > 0
        }

        return isSuccess
    }

    private fun GameDataTable?.mapIntoDataModel(): GameDataModel {
        return GameDataModel(
            this?.id,
            this?.slug,
            this?.name,
            this?.description,
            this?.released,
            this?.image,
            this?.rating,
            this?.playtime,
            this?.publishers,
            this?.developers
        )
    }

    private fun GameDataModel?.mapIntoDataTable(): GameDataTable {
        return GameDataTable(
            this?.id,
            this?.slug,
            this?.name,
            this?.description,
            this?.released,
            this?.image,
            this?.rating,
            this?.playtime,
            this?.publishers,
            this?.developers
        )
    }
}