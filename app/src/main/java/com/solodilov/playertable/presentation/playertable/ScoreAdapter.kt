package com.solodilov.playertable.presentation.playertable

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.solodilov.playertable.R
import com.solodilov.playertable.databinding.ItemScoreBinding

class ScoreAdapter(
    private val playerId: Int,
    private val onClick: (playerId: Int, opponentId: Int, score: Int?) -> Unit,
) : ListAdapter<Int?, ScoreViewHolder>(ScoreDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder =
        ScoreViewHolder(
            ItemScoreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val rowPos = holder.adapterPosition + 1
        when (playerId) {
            0 -> with(holder.editTextScore) {
                setText(rowPos.toString())
                isEnabled = false
            }
            rowPos -> with(holder.editTextScore) {
                setBackgroundColor(Color.BLACK)
                isEnabled = false
            }
            else -> {
                val currentScore = currentList[rowPos - 1] ?: ""
                with(holder.editTextScore){
                    setText(currentScore.toString())
                    addTextChangedListener(object : TextWatcher {
                        override fun afterTextChanged(editable: Editable?) {}

                        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int, ) {}

                        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int, ) {
                            val number = try {
                                setTextColor(getColor(context, R.color.gray))
                                s.toString().toInt()
                            } catch (e: Exception) {
                                null
                            }
                            val result = if (number in 0..5 || number == null) {
                                number
                            } else {
                                setTextColor(getColor(context, R.color.error))
                                Toast.makeText(context, context.getString(R.string.wrong_score), Toast.LENGTH_SHORT).show()
                                null
                            }
                            onClick(playerId, rowPos, result)
                        }
                    })
                }
            }
        }
    }

}

class ScoreViewHolder(
    private val binding: ItemScoreBinding,
) : RecyclerView.ViewHolder(binding.root) {

    val editTextScore = binding.editTextScore
}

private class ScoreDiffCallback : DiffUtil.ItemCallback<Int?>() {

    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }
}