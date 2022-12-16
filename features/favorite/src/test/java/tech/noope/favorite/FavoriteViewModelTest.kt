package tech.noope.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tech.noope.common.domain.RawgRepository
import tech.noope.common.domain.data.createGameDataPreview

@ExperimentalCoroutinesApi
class FavoriteViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    val dispatcher = TestCoroutineDispatcher()

    private var viewModel: FavoriteViewModel? = null

    private val rawgRepository = mockk<RawgRepository>(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        viewModel = FavoriteViewModel(rawgRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `get favorite games - success`() {
        val mockResponse = listOf(createGameDataPreview())

        coEvery {
            rawgRepository.getFavoriteGames()
        } returns mockResponse

        viewModel?.setEvent(FavoriteContract.Event.GetGames)

        val result = viewModel?.viewState?.value?.games
        assert(result?.isNotEmpty() == true)
    }
}