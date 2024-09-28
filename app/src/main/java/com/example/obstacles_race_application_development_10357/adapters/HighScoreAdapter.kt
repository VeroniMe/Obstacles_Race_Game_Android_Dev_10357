package com.example.obstacles_race_application_development_10357.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.obstacles_race_application_development_10357.R
import com.example.obstacles_race_application_development_10357.models.HighScoresList
import com.example.obstacles_race_application_development_10357.models.Score

class HighScoreAdapter(
    private val scoreList: HighScoresList,
    private val onItemClick: (Score) -> Unit
) : RecyclerView.Adapter<HighScoreAdapter.ScoreViewHolder>() {

    inner class ScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val playerNameTextView: TextView = itemView.findViewById(R.id.playerNameTextView)
        val scoreValueTextView: TextView = itemView.findViewById(R.id.scoreValueTextView)

        fun bind(score: Score) {
            playerNameTextView.text = score.name
            scoreValueTextView.text = score.scorePoints.toString()

            itemView.setOnClickListener {
                onItemClick(score)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.high_score_item, parent, false)
        return ScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        holder.bind(scoreList.highScoresList[position])
    }

    override fun getItemCount() = scoreList.highScoresList.size
}
