package com.djv.tmgchallenge.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.djv.tmgchallenge.data.model.Game
import com.djv.tmgchallenge.data.model.Ranking
import com.djv.tmgchallenge.databinding.ItemHomeListBinding

class GameAdapter : RecyclerView.Adapter<GameAdapter.GameAdapterViewHolder>() {

    private var gameList: List<Ranking> = listOf()

    fun setList(list: List<Ranking>) {
        this.gameList = list
        notifyDataSetChanged()
    }

    inner class GameAdapterViewHolder(val binding: ItemHomeListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameAdapterViewHolder {
        val binding = ItemHomeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameAdapterViewHolder, position: Int) {
        val listItem: Ranking = gameList[position]
        holder.binding.homeTitleTxt.text = listItem.playerName
        holder.binding.homeGameScore.text = listItem.mainCount.toString()
        holder.binding.homeWinsScore.text = (listItem.wins + listItem.visitorCount).toString()
    }

    override fun getItemCount(): Int = gameList.size
}