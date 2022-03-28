package com.djv.tmgchallenge.ui.players

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djv.tmgchallenge.data.model.Player
import com.djv.tmgchallenge.domain.GameUseCases
import com.djv.tmgchallenge.ui.model.RegisterPlayer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val gameUseCases: GameUseCases
) : ViewModel() {

    private val getPlayerList = MutableLiveData<List<Player>>()
    fun getPlayerLv(): LiveData<List<Player>> = getPlayerList

    private val isFetchLv = MutableLiveData<Unit>()
    fun getFetch(): LiveData<Unit> = isFetchLv

    private val notifyAdapterLv = MutableLiveData<Unit>()
    fun getNotifyAdapter(): LiveData<Unit> = notifyAdapterLv

    private val isPlayerExistLv = MutableLiveData<Pair<Boolean, Player>>()
    fun getPlayerExist(): LiveData<Pair<Boolean, Player>> = isPlayerExistLv

    private val isDialogButtonClickedLv = MutableLiveData<RegisterPlayer>()
    fun getButtonDialogClicked(): LiveData<RegisterPlayer> = isDialogButtonClickedLv

    fun getPlayerList() {
        viewModelScope.launch {
            try {
                gameUseCases.getAllPlayers()
                    .collect {
                        if (it.isEmpty()) {
                            isFetchLv.postValue(Unit)
                        } else {
                            getPlayerList.postValue(it)
                        }
                    }
            } catch (e: Throwable) {
                Log.e("ERROR", e.message.toString())
            }
        }
    }

    fun fetchPlayers() {
        viewModelScope.launch {
            try {
                gameUseCases.initPlayers()
                getPlayerList()
            } catch (e: Throwable) {
                Log.e("ERROR", e.message.toString())
            }
        }
    }

    fun deletePlayer(player: Player) {
        viewModelScope.launch {
            try {
                gameUseCases.deletePlayer(player)
                notifyAdapterLv.postValue(Unit)
            } catch (e: Throwable) {
                Log.e("ERROR", e.message.toString())
            }
        }
    }

    fun checkPlayerExist(player: Player?, isUpdate: Boolean) {
        if (isUpdate) {
            viewModelScope.launch {
                try {
                    gameUseCases.getPlayerByName(player!!.name)
                        .collect {
                            if (it != null) {
                                isPlayerExistLv.postValue(Pair(true, player))
                            } else {
                                isPlayerExistLv.postValue(Pair(false, player))
                            }
                        }
                } catch (e: Throwable) {
                    Log.e("ERROR", e.message.toString())
                }
            }
        } else {
            viewModelScope.launch {
                try {
                    gameUseCases.insertPlayer(player!!)
                    notifyAdapterLv.postValue(Unit)
                } catch (e: Throwable) {
                    Log.e("ERROR", e.message.toString())
                }
            }
        }
    }

    fun updatePlayer(player: Player) {
        viewModelScope.launch {
            try {
                gameUseCases.updatePlayer(player)
                notifyAdapterLv.postValue(Unit)
            } catch (e: Throwable) {
                Log.e("DELETE_ERROR", e.message.toString())
            }
        }
    }

}