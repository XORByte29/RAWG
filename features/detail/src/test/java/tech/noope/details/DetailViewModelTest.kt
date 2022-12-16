package tech.noope.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tech.noope.common.domain.RawgRepository
import tech.noope.common.domain.data.createGameDataPreview

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    val dispatcher = TestCoroutineDispatcher()

    private var viewModel: DetailViewModel? = null

    private val rawgRepository = mockk<RawgRepository>(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        viewModel = DetailViewModel(rawgRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `get favorite games - success`() {
        val mockResponse = createGameDataPreview()

        coEvery {
            rawgRepository.getGame(any())
        } returns mockResponse

        coEvery {
            rawgRepository.getFavoriteGame(any())
        } returns mockResponse

        viewModel?.setEvent(DetailContract.Event.LoadGame(0))

        val result = viewModel?.viewState?.value?.game
        assertEquals(result, mockResponse)
    }
}