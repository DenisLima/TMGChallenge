package com.djv.tmgchallenge.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.djv.tmgchallenge.data.model.Player
import com.djv.tmgchallenge.databinding.ItemPlayerListBinding

class PlayerAdapter(
    private val onClickListener: HandleClick
) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    private var playerList: List<Player> = listOf()

    fun setList(list: List<Player>) {
        this.playerList = list
        notifyDataSetChanged()
    }

    inner class PlayerViewHolder(val binding: ItemPlayerListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val binding = ItemPlayerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val listItem: Player = playerList[position]
        holder.binding.playerTitleTxt.text = listItem.name

        holder.binding.deleteButton.setOnClickListener {
            onClickListener.onDeleteCLick(listItem)
        }

        holder.itemView.setOnClickListener {
            onClickListener.onItemClick(listItem)
        }
    }

    override fun getItemCount(): Int = playerList.size

    interface HandleClick {
        fun onDeleteCLick(player: Player)
        fun onItemClick(player: Player)
    }

}