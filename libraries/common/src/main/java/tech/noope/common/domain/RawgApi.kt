package tech.noope.common.domain

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import tech.noope.common.domain.data.BaseResponse
import tech.noope.common.domain.data.GameDataModel

interface RawgApi {

    @GET(RawgUrl.URL_LIST_GAME)
    suspend fun getGames(
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
        @Query("search") search: String,
        @Query("key") key: String = RawgUrl.RAWG_API_KEY
    ) : BaseResponse

    @GET(RawgUrl.URL_GAME_DETAIL)
    suspend fun getGame(
        @Path(RawgUrl.PARAM_GAME_ID) id: Int,
        @Query("key") key: String = RawgUrl.RAWG_API_KEY,
    ) : GameDataModel
}