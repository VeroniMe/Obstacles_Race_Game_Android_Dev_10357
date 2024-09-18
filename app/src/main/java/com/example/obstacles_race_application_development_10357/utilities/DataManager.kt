package com.example.obstacles_race_application_development_10357.utilities

import com.example.obstacles_race_application_development_10357.models.HighScoresList
import com.example.obstacles_race_application_development_10357.models.Score

object DataManager {

    fun generateHighScoreList() : HighScoresList {
        return HighScoresList
            .Builder()
            .addScore(
                Score
                    .Builder()
                    .name("Veronika")
                    .scorePoints(10)
                    .lan(32.0)
                    .lng(34.0)
                    .build()
            )
            .addScore(
                Score
                    .Builder()
                    .name("Bogdan")
                    .scorePoints(15)
                    .lan(32.1)
                    .lng(34.0)
                    .build()
            ).build()
    }
}