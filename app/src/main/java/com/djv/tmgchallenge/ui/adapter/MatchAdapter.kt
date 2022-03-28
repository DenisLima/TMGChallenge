package com.djv.tmgchallenge.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.djv.tmgchallenge.R
import com.djv.tmgchallenge.data.model.PlayerAndGame
import com.djv.tmgchallenge.databinding.ItemMatchListBinding

class MatchAdapter(
    private val handleDeleteMatch: HandleDeleteMatch
): RecyclerView.Adapter<MatchAdapter.MatchViewHolder>() {

    private var playerList: List<PlayerAndGame> = listOf()

    fun setList(list: List<PlayerAndGame>) {
        this.playerList = list
        notifyDataSetChanged()
    }

    inner class MatchViewHolder(val binding: ItemMatchListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val binding = ItemMatchListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val listItem: PlayerAndGame = playerList[position]
        holder.binding.matchDateText.text = listItem.dateRegister

        if (listItem.mainScore < listItem.secondScore) {
            holder.binding.matchStatusText.text = holder.itemView.context.getString(R.string.result_lose)
        } else {
            holder.binding.matchStatusText.text = holder.itemView.context.getString(R.string.result_wins)
        }

        holder.binding.matchMainNameText.text = listItem.mainName
        holder.binding.matchMainScoreText.text = listItem.mainScore.toString()

        holder.binding.matchVisitorNameText.text = listItem.secondName
        holder.binding.matchVisitorScoreText.text = listItem.secondScore.toString()

        holder.binding.trashIcon.setOnClickListener {
            handleDeleteMatch.onDeleteClicked(listItem)
        }
    }

    override fun getItemCount(): Int = playerList.size

    interface HandleDeleteMatch {
        fun onDeleteClicked(game: PlayerAndGame)
    }
}