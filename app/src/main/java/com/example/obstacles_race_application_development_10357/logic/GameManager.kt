package com.example.obstacles_race_application_development_10357.logic

class GameManager(private val lifeCount: Int = 3) {
    var collisionsCount: Int = 0
        private set

    val isGameEnded: Boolean
        get() = collisionsCount == lifeCount


    fun checkCollision() {
        //TODO: continue logic of collision check

    }
}