package com.solodilov.playertable.presentation.playertable

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.solodilov.playertable.R
import com.solodilov.playertable.databinding.ItemPlayerBinding
import com.solodilov.playertable.presentation.model.PlayerUi

class TableAdapter(
    private val onClick: (playerId: Int, opponentId: Int, score: Int?) -> Unit,
) : ListAdapter<PlayerUi, PlayerViewHolder>(PlayerDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder =
        PlayerViewHolder(
            ItemPlayerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val adapter = ScoreAdapter(position, onClick)
        holder.scoreList.adapter = adapter
        adapter.submitList(getItem(position).scores)
        if (position == 0) {
            holder.headerBind()
        } else {
            holder.bind(getItem(position))
        }
    }

}

class PlayerViewHolder(
    private val binding: ItemPlayerBinding,
) : RecyclerView.ViewHolder(binding.root) {

    val scoreList = binding.scoreList

    fun headerBind() {
        with(binding) {
            playerName.text = ""
            playerId.text = ""
            sumScore.text = itemView.context.getString(R.string.sum_score)
            playerRating.text = itemView.context.getString(R.string.rating)
        }
    }

    fun bind(player: PlayerUi) {
        with(binding) {
            playerName.text = player.name
            playerId.text = player.id.toString()
            sumScore.text = player.sumScore?.toString() ?: ""
            playerRating.text = player.rating?.toString() ?: ""
        }
    }
}

private class PlayerDiffCallback : DiffUtil.ItemCallback<PlayerUi>() {

    override fun areItemsTheSame(oldItem: PlayerUi, newItem: PlayerUi): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PlayerUi, newItem: PlayerUi): Boolean {
        return oldItem == newItem
    }
}