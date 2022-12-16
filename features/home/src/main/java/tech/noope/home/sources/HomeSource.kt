package tech.noope.home.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import tech.noope.common.domain.data.GameDataModel
import tech.noope.common.domain.RawgRepository

class HomeSource(
    private val keyword: String,
    private val repository: RawgRepository
) : PagingSource<Int, GameDataModel>() {

    override fun getRefreshKey(state: PagingState<Int, GameDataModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GameDataModel> {
        return try {
            val currentPage = params.key ?: 1
            val response = repository.getGames(page = currentPage, search = keyword)

            LoadResult.Page(
                data = response.results,
                prevKey = if (response.results.isNotEmpty()) currentPage - 1 else null,
                nextKey = if (response.results.isNotEmpty()) currentPage + 1 else null,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}