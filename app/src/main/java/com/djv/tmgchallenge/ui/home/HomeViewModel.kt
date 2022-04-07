package com.djv.tmgchallenge.ui.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.djv.tmgchallenge.data.model.Ranking
import com.djv.tmgchallenge.domain.GameUseCases
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
    private val gameUseCases: GameUseCases
) : ViewModel() {

    private val getListLv = MutableLiveData<List<Ranking>>()
    fun getList(): LiveData<List<Ranking>> = getListLv

    @SuppressLint("CheckResult")
    fun fetchList() {
        var par = 0
        var rankList = mutableListOf<Ranking>()
        gameUseCases.getAllPlayers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { listPlayer ->
                    listPlayer.forEach { player ->
                        gameUseCases.getRanking(player)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeBy(
                                onSuccess = { rank ->
                                    rankList.add(rank)
                                    if (listPlayer.size == par) {
                                        getListLv.postValue(
                                            rankList.sortedWith(
                                                compareBy({ it.mainCount },
                                                    { (it.wins + it.visitorCount) })
                                            ).asReversed()
                                        )
                                    }
                                },
                                onError = {
                                    Log.e("ERROR", it.message.toString())
                                }
                            )
                        par++
                    }
                },
                onError = {
                    Log.e("ERROR", it.message.toString())
                }
            )
    }
}