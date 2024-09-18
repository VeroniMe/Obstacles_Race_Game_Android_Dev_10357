package com.example.obstacles_race_application_development_10357.models

import com.example.obstacles_race_application_development_10357.utilities.Constants

data class HighScoresList private constructor(
    val highScoresList : List<Score>
) {
    class Builder (
        var highScoresList : List<Score> = mutableListOf()
    ) {
        fun addScore( newScore : Score) = apply {

            val mutableList = this.highScoresList.toMutableList()
            mutableList.add(newScore)
            mutableList.sortByDescending { it.scorePoints }

            // Keep only the top 10 scores
            if (mutableList.size > Constants.HIGH_SCORES_LIST_SIZE) {
                mutableList.removeAt(mutableList.size - 1) // Remove the lowest score
            }
            this.highScoresList = mutableList

        }
        fun build() = HighScoresList(highScoresList)
        //TODO: CHECK IF NEED TO ADD NEWSCORE - 10 HIGHEST SAVED
    }

}
