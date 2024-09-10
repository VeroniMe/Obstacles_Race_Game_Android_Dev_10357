package com.example.obstacles_race_application_development_10357.logic

import kotlin.random.Random

class GameManager(
    private var lifeCount: Int = 3,
    private val obsCols: Int = 3,
    private val obsRows: Int = 5,
) {

    var collisionsCount: Int = 0
        private set
    var ufoPosition: Int = 1
        private set
    var obstacles: Array<Array<Boolean>> = Array(obsRows) { Array(obsCols) { false } }

    val isGameLost: Boolean
        get() = collisionsCount == lifeCount


    fun moveLeft() {
        ufoPosition--
        if(ufoPosition < 0)
            ufoPosition = 0
        checkCollision()
    }

    fun moveRight() {
        ufoPosition++
        if(ufoPosition > 2)
            ufoPosition = 2
        checkCollision()
    }

    fun checkCollision() {
        if(obstacles[obsRows-1][ufoPosition]) { //if there is obstacle in ufo position
            collisionsCount++
            obstacles[obsRows-1] = Array(obsCols) { false }

        }

    }

    fun moveObstacles( addNewLine:Boolean ) {

        for (i in obstacles.size - 1 downTo 1)  //from obsRows to 0
            obstacles[i] = obstacles[i-1]

        obstacles[0] = Array(obsCols) { false } //put first row all false

        if(addNewLine) {
            val randomObs = Random.nextInt(0, obsCols) // generates a random integer between 0 and obsCols-1
            for (i in obstacles[0].indices) {
                obstacles[0][i] = i == randomObs
            }
        }
        checkCollision()

    }

    fun resetGameLogic() {
        lifeCount = 3
        collisionsCount = 0

    }


}