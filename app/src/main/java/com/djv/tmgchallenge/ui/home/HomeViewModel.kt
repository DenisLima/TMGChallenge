package com.djv.tmgchallenge.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djv.tmgchallenge.data.model.Game
import com.djv.tmgchallenge.data.model.Player
import com.djv.tmgchallenge.data.model.Ranking
import com.djv.tmgchallenge.domain.GameUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val gameUseCases: GameUseCases
) : ViewModel() {

    private val getListLv = MutableLiveData<List<Ranking>>()
    fun getList(): LiveData<List<Ranking>> = getListLv

    fun fetchList() {
        var rankList = mutableListOf<Ranking>()
        viewModelScope.launch {
            try {
                gameUseCases.getAllPlayers().collect {
                    it.forEach {  player ->
                        withContext(Dispatchers.IO) {
                            gameUseCases.getRanking(player)
                                .collect { rank ->
                                    rankList.add(rank)
                                }
                        }
                    }
                }
                getListLv.postValue(rankList.sortedWith(compareBy({it.mainCount},{(it.wins+it.visitorCount)})).asReversed())
            } catch (e: Throwable) {
                Log.e("ERROR", e.message.toString())
            }
        }
    }
}