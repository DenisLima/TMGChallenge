package com.djv.tmgchallenge.ui.matches

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djv.tmgchallenge.data.model.Game
import com.djv.tmgchallenge.data.model.Player
import com.djv.tmgchallenge.data.model.PlayerAndGame
import com.djv.tmgchallenge.domain.GameUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MatchViewModel(
    private val gameUseCases: GameUseCases
): ViewModel() {

    private val fetchGamesLv = MutableLiveData<List<PlayerAndGame>>()
    fun getFetchGames(): LiveData<List<PlayerAndGame>> = fetchGamesLv

    private val isFetchDataLv = MutableLiveData<Unit>()
    fun getFetchData(): LiveData<Unit> = isFetchDataLv

    private val playerListLv = MutableLiveData<List<Player>>()
    fun getPlayers(): LiveData<List<Player>> = playerListLv

    private val notifyAdapterLv = MutableLiveData<Unit>()
    fun getNotifyAdapter(): LiveData<Unit> = notifyAdapterLv

    private val isSamePlayerLv = MutableLiveData<Unit>()
    fun getSamePlayer(): LiveData<Unit> = isSamePlayerLv

    fun fetchGames() {
        viewModelScope.launch {
            try {
                gameUseCases.getPlayerAndGame()
                    .collect {
                        if (it.isEmpty()) {
                            insertInitGames()
                        } else {
                            fetchGamesLv.postValue(it)
                        }
                    }
            } catch (e: Throwable) {
                Log.e("ERROR", e.message.toString())
            }
        }
    }

    private fun insertInitGames() {
        viewModelScope.launch {
            try {
                gameUseCases.initGames()
                isFetchDataLv.postValue(Unit)
            } catch (e: Throwable) {
                Log.e("ERROR", e.message.toString())
            }
        }
    }

    fun getPlayerList() {
        viewModelScope.launch {
            try {
                gameUseCases.getAllPlayers()
                    .collect {
                        playerListLv.postValue(it)
                    }
            } catch (e: Throwable) {
                Log.e("ERROR", e.message.toString())
            }
        }
    }

    fun insertGame(game: Game) {
        if (game.mainPlayer == game.secondPlayer) {
            isSamePlayerLv.postValue(Unit)
        } else {
            viewModelScope.launch {
                try {
                    gameUseCases.insertGames(game)
                    isFetchDataLv.postValue(Unit)
                } catch (e: Throwable) {
                    Log.e("ERROR", e.message.toString())
                }
            }
        }
    }

    fun deleteGame(game: PlayerAndGame) {
        viewModelScope.launch {
            try {
                gameUseCases.deleteGameByid(game.idGame)
                notifyAdapterLv.postValue(Unit)
            } catch (e: Throwable) {
                Log.e("ERROR", e.message.toString())
            }
        }
    }

}