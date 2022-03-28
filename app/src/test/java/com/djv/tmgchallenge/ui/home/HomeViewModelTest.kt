package com.djv.tmgchallenge.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.djv.tmgchallenge.domain.GameUseCases
import com.djv.tmgchallenge.ui.BaseTest
import com.djv.tmgchallenge.ui.PlayersMock
import com.djv.tmgchallenge.ui.RankingMock
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*

@ExperimentalCoroutinesApi
class HomeViewModelTest : BaseTest(){

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()

    @MockK
    lateinit var gameUseCases: GameUseCases

    private val viewModel by lazy {
        spyk(
            HomeViewModel(
                gameUseCases = gameUseCases
            )
        )
    }

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN open home screen WHEN fetch is needed THEN fetch with success`() {
        dispatcher.runBlockingTest {
            //Given
            coEvery {
                gameUseCases.getAllPlayers()
            } returns flowOf(PlayersMock.LIST_PLAYERS_MOCK)

            coEvery {
                gameUseCases.getRanking(PlayersMock.FAKE_PLAYER)
            } returns flowOf(RankingMock.FAKE_RANKING)

            //When
            viewModel.fetchList()

            //Then
            Assert.assertEquals(
                listOf(RankingMock.FAKE_RANKING),
                viewModel.getList().blockingObserve()
            )
        }
    }
}