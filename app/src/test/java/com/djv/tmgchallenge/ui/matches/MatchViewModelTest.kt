package com.djv.tmgchallenge.ui.matches

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.djv.tmgchallenge.domain.GameUseCases
import com.djv.tmgchallenge.ui.BaseTest
import com.djv.tmgchallenge.ui.GameMock
import com.djv.tmgchallenge.ui.PlayerAndGameMock
import com.djv.tmgchallenge.ui.PlayersMock
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
import org.junit.Assert.assertEquals

@ExperimentalCoroutinesApi
class MatchViewModelTest: BaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()

    @MockK
    lateinit var gameUseCases: GameUseCases

    private val viewModel by lazy {
        spyk(
            MatchViewModel(
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
    fun `GIVEN open game screen WHEN fetch is needed THEN fetch with success`() {
        dispatcher.runBlockingTest {
            //Given
            coEvery {
                gameUseCases.getPlayerAndGame()
            } returns flowOf(PlayerAndGameMock.FAKE_PLAYERANDGAME)

            //When
            viewModel.fetchGames()

            //Then
            assertEquals(PlayerAndGameMock.FAKE_PLAYERANDGAME, viewModel.getFetchGames().blockingObserve())
        }
    }

    @Test
    fun `GIVEN need players WHEN enter game screen THEN get players with success`() {
        dispatcher.runBlockingTest {
            //Given
            coEvery {
                gameUseCases.getAllPlayers()
            } returns flowOf(PlayersMock.LIST_PLAYERS_MOCK)

            //When
            viewModel.getPlayerList()

            //Then
            assertEquals(PlayersMock.LIST_PLAYERS_MOCK, viewModel.getPlayers().blockingObserve())
        }
    }

    @Test
    fun `GIVEN need insert game WHEN register same games THEN show message`() {
        dispatcher.runBlockingTest {
            //Given
            coEvery {
                gameUseCases.insertGames(GameMock.FAKE_GAME)
            } returns Unit

            //When
            viewModel.insertGame(GameMock.FAKE_GAME)

            //Then
            assertEquals(Unit, viewModel.getSamePlayer().blockingObserve())
        }
    }

    @Test
    fun `GIVEN need insert game WHEN register diferent games THEN insert with success`() {
        dispatcher.runBlockingTest {
            //Given
            coEvery {
                gameUseCases.insertGames(GameMock.FAKE_DIFERENT_GAME)
            } returns Unit

            //When
            viewModel.insertGame(GameMock.FAKE_DIFERENT_GAME)

            //Then
            assertEquals(Unit, viewModel.getFetchData().blockingObserve())
        }
    }

    @Test
    fun `GIVEN need delete game WHEN delete plaery THEN delete with success`() {
        dispatcher.runBlockingTest {
            //Given
            coEvery {
                gameUseCases.deleteGameByid(GameMock.FAKE_GAME.id)
            } returns Unit

            //When
            viewModel.deleteGame(PlayerAndGameMock.FAKE_PLAYERANDGAME_SIMPLE)

            //Then
            assertEquals(Unit, viewModel.getNotifyAdapter().blockingObserve())
        }
    }
}