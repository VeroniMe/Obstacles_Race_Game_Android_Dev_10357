package com.example.obstacles_race_application_development_10357

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.obstacles_race_application_development_10357.logic.GameManager
import com.example.obstacles_race_application_development_10357.utilities.Constants
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {


   private lateinit var main_IMG_hearts : Array <ShapeableImageView>
   private lateinit var main_BTN_left : ExtendedFloatingActionButton
   private lateinit var main_BTN_start : MaterialButton
   private lateinit var main_BTN_right : ExtendedFloatingActionButton
   private lateinit var main_IMG_asteroids : Array<Array<ShapeableImageView>>
   private lateinit var main_IMG_ufos : Array<ShapeableImageView>
   private lateinit var gameManager: GameManager

   private var timerOn = false
   private var justStarted: Boolean = true
   private var lastNewObsAppearance: Long = 0



   private lateinit var gameJob : Job


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews()
        gameManager = GameManager(main_IMG_hearts.size, main_IMG_ufos.size, main_IMG_asteroids.get(0).size, main_IMG_asteroids.size)
        initViews()
        refreshUI()
    }

    override fun onStop() {
        super.onStop()
        stopTimer()
    }

    private fun initViews() {
        main_BTN_start.setOnClickListener { view: View? -> obstaclesMove()}
        main_BTN_left.setOnClickListener { view: View? -> moveLeft()}
        main_BTN_right.setOnClickListener { view: View? -> moveRight()}
    }

    private fun stopTimer() {
        timerOn = false
        gameJob.cancel()
    }

    private fun moveLeft() {
       gameManager.moveLeft()
       refreshUI()
    }

    private fun moveRight() {
        gameManager.moveRight()
        refreshUI()
    }

    private fun refreshUI() {
        for (i in main_IMG_ufos.indices) {
            if(i == gameManager.ufoPosition)
                main_IMG_ufos[i].visibility = View.VISIBLE
            else
                main_IMG_ufos[i].visibility = View.INVISIBLE
        }
        for (i in gameManager.obstacles.indices) {
            for ( j in gameManager.obstacles[i].indices) {
                if(gameManager.obstacles[i][j])
                    main_IMG_asteroids[i][j].visibility = View.VISIBLE
                else
                    main_IMG_asteroids[i][j].visibility = View.INVISIBLE

            }
        }

    }

    private fun obstaclesMove(){
        println("obstaclesMove")
        if(!timerOn) {
            timerOn = true
            gameJob = lifecycleScope.launch {
                while(timerOn) {
                    var newObstacles = false
                    if(justStarted) {
                        newObstacles = true
                        justStarted = false
                    }
                    val currentTime = System.currentTimeMillis()
                    if(currentTime - lastNewObsAppearance > Constants.OBSTACLES_APPEARANCE) {
                        newObstacles = true
                        lastNewObsAppearance = currentTime
                    }
                    gameManager.moveObstacles(newObstacles) //calculate new positions
                    refreshUI()
                    delay(Constants.SPEED)
                }
            }
        }

    }

    private fun findViews() {
        main_BTN_left = findViewById(R.id.main_BTN_left)
        main_BTN_start = findViewById(R.id.main_BTN_start)
        main_BTN_right = findViewById(R.id.main_BTN_right)
        main_IMG_hearts = arrayOf(
            findViewById(R.id.main_IMG_heart1),
            findViewById(R.id.main_IMG_heart2),
            findViewById(R.id.main_IMG_heart3)
        )
        main_IMG_ufos = arrayOf(
            findViewById(R.id.main_IMG_ufo0),
            findViewById(R.id.main_IMG_ufo1),
            findViewById(R.id.main_IMG_ufo2)
        )
        main_IMG_asteroids = arrayOf(
            arrayOf(
                findViewById(R.id.main_IMG_asteroid00),
                findViewById(R.id.main_IMG_asteroid01),
                findViewById(R.id.main_IMG_asteroid02)
            ),
            arrayOf(
                findViewById(R.id.main_IMG_asteroid10),
                findViewById(R.id.main_IMG_asteroid11),
                findViewById(R.id.main_IMG_asteroid12)
            ),
            arrayOf(
                findViewById(R.id.main_IMG_asteroid20),
                findViewById(R.id.main_IMG_asteroid21),
                findViewById(R.id.main_IMG_asteroid22)
            ),
            arrayOf(
                findViewById(R.id.main_IMG_asteroid30),
                findViewById(R.id.main_IMG_asteroid31),
                findViewById(R.id.main_IMG_asteroid32)
            ),
            arrayOf(
                findViewById(R.id.main_IMG_asteroid40),
                findViewById(R.id.main_IMG_asteroid41),
                findViewById(R.id.main_IMG_asteroid42)
            )



        )
    }


}