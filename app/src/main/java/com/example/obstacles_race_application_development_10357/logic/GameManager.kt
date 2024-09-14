package com.example.obstacles_race_application_development_10357.logic

import com.example.obstacles_race_application_development_10357.utilities.Shapes
import kotlin.random.Random

class GameManager(
    private var lifeCount: Int = 3,
    private val obsCols: Int = 3,
    private val obsRows: Int = 5,
) {

    var score : Int = 0
        private set
    var collisionsCount: Int = 0
        private set
    var ufoPosition: Int = 2
        private set
    var obstacles: Array<Array<Shapes>> = Array(obsRows) { Array(obsCols) { Shapes.EMPTY } }

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
        if(ufoPosition > 4)
            ufoPosition = 4
        checkCollision()
    }

    fun checkCollision() {
        if(obstacles[obsRows-1][ufoPosition] == Shapes.OBSTACLE) { //if there is obstacle in ufo position
            collisionsCount++
            obstacles[obsRows-1] = Array(obsCols) { Shapes.EMPTY }

        } else if (obstacles[obsRows-1][ufoPosition] == Shapes.COIN) { //if there is coin shape
            score += COIN_POINTS
            obstacles[obsRows-1] = Array(obsCols) { Shapes.EMPTY }
        }

    }

    fun moveObstacles( addNewLine:Boolean ) {

        for (i in obstacles.size - 1 downTo 1)  //from obsRows to 0
            obstacles[i] = obstacles[i-1]

        obstacles[0] = Array(obsCols) { Shapes.EMPTY } //put first row all false

        if(addNewLine) {
            val randomPos = Random.nextInt(0, obsCols) // generates a random integer between 0 and obsCols-1 for position
            val randomShape = Random.nextInt(1, Shapes.NUM_OF_SHAPES.ordinal) // generates a random integer between 1 and Shapes.NUM_OF_SHAPES-1
            for (i in obstacles[0].indices) {
                if(i == randomPos)
                    obstacles[0][i] = Shapes.entries[randomShape]
                //TODO: may be we need to put Shapes.EMPTY in remaining cells here
            }
        }
        checkCollision()

    }

    fun resetGameLogic() {
        lifeCount = 3
        collisionsCount = 0

    }

    fun centerPosition() {
        ufoPosition = 2
    }

    companion object {
        private const val COIN_POINTS = 5
    }


}