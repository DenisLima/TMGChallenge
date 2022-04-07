package com.djv.tmgchallenge.ui.players

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djv.tmgchallenge.data.model.Player
import com.djv.tmgchallenge.domain.GameUseCases
import com.djv.tmgchallenge.ui.model.RegisterPlayer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
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

    @SuppressLint("CheckResult")
    fun getPlayerList() {
        gameUseCases.getAllPlayers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    if (it.isEmpty()) {
                        isFetchLv.postValue(Unit)
                    } else {
                        getPlayerList.postValue(it)
                    }
                },
                onError = {
                    Log.e("ERROR", it.message.toString())
                }
            )
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

    @SuppressLint("CheckResult")
    fun checkPlayerExist(player: Player?, isUpdate: Boolean) {
        if (isUpdate) {
            gameUseCases.getPlayerByName(player!!.name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        if (it != null) {
                            isPlayerExistLv.postValue(Pair(true, player))
                        } else {
                            isPlayerExistLv.postValue(Pair(false, player))
                        }
                    },
                    onError = {
                        Log.e("ERROR", it.message.toString())
                    }
                )
        } else {
            gameUseCases.insertPlayer(player!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onComplete = {
                        notifyAdapterLv.postValue(Unit)
                    },
                    onError = {
                        Log.e("ERROR", it.message.toString())
                    }
                )
        }
    }

    @SuppressLint("CheckResult")
    fun updatePlayer(player: Player) {
        gameUseCases.updatePlayer(player)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    notifyAdapterLv.postValue(Unit)
                },
                onError = {
                    Log.e("DELETE_ERROR", it.message.toString())
                }
            )
    }

}