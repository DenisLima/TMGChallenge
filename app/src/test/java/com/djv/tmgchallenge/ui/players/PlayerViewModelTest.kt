package com.djv.tmgchallenge.ui.players

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.djv.tmgchallenge.domain.GameUseCases
import com.djv.tmgchallenge.ui.BaseTest
import com.djv.tmgchallenge.ui.PlayersMock
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PlayerViewModelTest : BaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()

    @MockK
    lateinit var gameUseCases: GameUseCases

    private val viewModel by lazy {
        spyk(
            PlayerViewModel(
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
    fun `GIVEN open player screen WHEN get player list THEN get list with success`() {
        dispatcher.runBlockingTest {
            //Given
            coEvery {
                gameUseCases.getAllPlayers()
            } returns flowOf(PlayersMock.LIST_PLAYERS_MOCK)

            //When
            viewModel.getPlayerList()

            //Then
            assertTrue(viewModel.getPlayerLv().captureValues().isNotEmpty())
        }
    }

    @Test
    fun `GIVEN open player screen WHEN get player list THEN get empty list`() {
        dispatcher.runBlockingTest {
            //Given
            coEvery {
                gameUseCases.getAllPlayers()
            } returns flowOf(PlayersMock.LIST_PLAYERS_EMPTY)

            //When
            viewModel.getPlayerList()

            //Then
            assertTrue(viewModel.getPlayerLv().captureValues().isEmpty())
        }
    }

    @Test
    fun `GIVEN no value to players WHEN fetch players THEN fetch success`() {
        dispatcher.runBlockingTest {
            //Given
            coEvery {
                gameUseCases.getAllPlayers()
            } returns flowOf(PlayersMock.LIST_PLAYERS_MOCK)

            coEvery {
                gameUseCases.initPlayers()
            } returns Unit

            //When
            viewModel.fetchPlayers()

            //Then
            coVerify {
                viewModel.getPlayerList()
            }
        }
    }

    @Test
    fun `GIVEN user need delete player WHEN tapped delete button THEN delete success`() {
        dispatcher.runBlockingTest {
            //Given
            coEvery {
                gameUseCases.deletePlayer(PlayersMock.FAKE_PLAYER)
            } returns Unit

            //When
            viewModel.deletePlayer(PlayersMock.FAKE_PLAYER)

            //Then
            assertEquals(Unit, viewModel.getNotifyAdapter().blockingObserve())
        }
    }

    @Test
    fun `GIVEN user need insert player WHEN exist player THEN update with success`() {
        dispatcher.runBlockingTest {
            //Given
            coEvery {
                gameUseCases.getPlayerByName(PlayersMock.FAKE_PLAYER.name)
            } returns flowOf(PlayersMock.FAKE_PLAYER)

            //When
            viewModel.checkPlayerExist(PlayersMock.FAKE_PLAYER, true)

            //Then
            assertEquals(Pair(true, PlayersMock.FAKE_PLAYER), viewModel.getPlayerExist().blockingObserve())
        }
    }

    @Test
    fun `GIVEN user need insert player WHEN tapped save button THEN insert with success`() {
        dispatcher.runBlockingTest {
            //Given
            coEvery {
                gameUseCases.insertPlayer(PlayersMock.FAKE_PLAYER)
            } returns Unit

            //When
            viewModel.checkPlayerExist(PlayersMock.FAKE_PLAYER, false)

            //Then
            assertEquals(Unit, viewModel.getNotifyAdapter().blockingObserve())
        }
    }

    @Test
    fun `GIVEN user need update player WHEN tapped update button THEN update with success`() {
        dispatcher.runBlockingTest {
            //Given
            coEvery {
                gameUseCases.updatePlayer(PlayersMock.FAKE_PLAYER)
            } returns Unit

            //When
            viewModel.updatePlayer(PlayersMock.FAKE_PLAYER)

            //Then
            assertEquals(Unit, viewModel.getNotifyAdapter().blockingObserve())
        }
    }
}