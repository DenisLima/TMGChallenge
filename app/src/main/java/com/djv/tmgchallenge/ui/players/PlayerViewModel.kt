package com.djv.tmgchallenge.ui.players

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.*
import androidx.room.EmptyResultSetException
import com.djv.tmgchallenge.data.model.Player
import com.djv.tmgchallenge.domain.GameUseCases
import com.djv.tmgchallenge.data.registerdata.PlayerAddData
import com.djv.tmgchallenge.ui.model.RegisterPlayer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class PlayerViewModel @Inject constructor(
    private val gameUseCases: GameUseCases,
    private val playerAddData: PlayerAddData
) : ViewModel() {

    // LiveData Region
    private val getPlayerList = MutableLiveData<List<Player>>()
    fun getPlayerLv(): LiveData<List<Player>> = getPlayerList

    private val isFetchLv = MutableLiveData<Unit>()
    fun getFetch(): LiveData<Unit> = isFetchLv

    private var notifyAdapterLv = MutableLiveData<Unit>()
    fun getNotifyAdapter(): LiveData<Unit> = notifyAdapterLv

    val publishSub = PublishSubject.create<Boolean>()

    private var isPlayerExistLv = MutableLiveData<Pair<Boolean, Player>>()
    fun getPlayerExist(): LiveData<Pair<Boolean, Player>> = isPlayerExistLv

    private val isDialogButtonClickedLv = MutableLiveData<RegisterPlayer>()
    fun getButtonDialogClicked(): LiveData<RegisterPlayer> = isDialogButtonClickedLv

    private var _isCloseDialog = MutableLiveData<Unit>()
    val isCloseDialog: LiveData<Unit> = _isCloseDialog
    // LiveData Region end

    //Calls region
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
        playerAddData.isRefreshList = false
    }

    @SuppressLint("CheckResult")
    fun fetchPlayers() {
        gameUseCases.initPlayers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    getPlayerList()
                },
                onError = {
                    Log.e("ERROR", it.message.toString())
                }
            )
    }

    @SuppressLint("CheckResult")
    fun deletePlayer(player: Player) {
        gameUseCases.deletePlayer(player)
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

    @SuppressLint("CheckResult")
    fun checkPlayerExist(player: Player?, isUpdate: Boolean) {
        playerAddData.isRefreshList = true
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
                        if(it is EmptyResultSetException) {
                            isPlayerExistLv.value = Pair(false, player)
                        }
                   }
                )
        } else {
            gameUseCases.insertPlayer(player!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onComplete = {
                        notifyAdapterLv.postValue(Unit)
                        publishSub.onNext(true)
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
                    _isCloseDialog.value = Unit
                    publishSub.onNext(true)
                    playerAddData.isRefreshList = true
                },
                onError = {
                    Log.e("DELETE_ERROR", it.message.toString())
                }
            )
    }
    // Calls region end

}