package com.example.obstacles_race_application_development_10357

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.obstacles_race_application_development_10357.interfaces.Callback_MoveCallback
import com.example.obstacles_race_application_development_10357.logic.GameManager
import com.example.obstacles_race_application_development_10357.utilities.Constants
import com.example.obstacles_race_application_development_10357.utilities.MoveDetector
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

   private lateinit var moveDetector: MoveDetector

   private var timerOn = false
   private var justStarted: Boolean = true
   private var collisions: Int = 0
   private var lastNewObsAppearance: Long = 0


   private lateinit var gameJob : Job


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews()
        gameManager = GameManager(
            main_IMG_hearts.size,
            main_IMG_asteroids[0].size,
            main_IMG_asteroids.size
        )
        initViews()
        initMoveDetector()
        refreshUI()
    }

    private fun initMoveDetector() {
        moveDetector = MoveDetector(
            this,
                    object : Callback_MoveCallback {
                        override fun moveLeftCall() {
                            moveLeft()
                        }
                        override fun moveRightCall() {
                            moveRight()
                        }

                        override fun centralize() {
                            centerPosition()
                        }

                    }
        )
    }

    override fun onPause() {
        super.onPause()
        moveDetector.stop()
    }

    override fun onResume() {
        super.onResume()
        moveDetector.start()
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

    private fun centerPosition() {
        gameManager.centerPosition()
        refreshUI()
    }

    private fun refreshUI() {


        //refresh ufo position view
        for (i in main_IMG_ufos.indices) {
            if(i == gameManager.ufoPosition)
                main_IMG_ufos[i].visibility = View.VISIBLE
            else
                main_IMG_ufos[i].visibility = View.INVISIBLE
        }

        if(gameManager.collisionsCount != 0) {

            main_IMG_hearts[main_IMG_hearts.size - gameManager.collisionsCount].visibility = View.INVISIBLE
            if(gameManager.collisionsCount > collisions) {

                toastAndVibrateOnCollision()
                collisions++

            }

        }
        //refresh obstacles positions view
        for (i in gameManager.obstacles.indices) {
            for ( j in gameManager.obstacles[i].indices) {

                if(gameManager.obstacles[i][j])
                    main_IMG_asteroids[i][j].visibility = View.VISIBLE
                else
                    main_IMG_asteroids[i][j].visibility = View.INVISIBLE

            }
        }
        if(gameManager.isGameLost) {
            Log.d(
                "Game Status",
                "Game Over!"
            )  //d - debug, e -error, i - info, w - warning, t - trace
            //TODO: continue to implement + change screen
            collisions = 0
            gameManager.resetGameLogic()
            main_IMG_hearts[0].visibility = View.VISIBLE
            main_IMG_hearts[1].visibility = View.VISIBLE
            main_IMG_hearts[2].visibility = View.VISIBLE
        }

    }

    //GAME FLOW
    private fun obstaclesMove(){
        main_BTN_start.visibility = View.INVISIBLE
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

    private fun toastAndVibrateOnCollision() {
        //TODO : Add toast and vibrate on collision

        toastOnCollision("Oops! You crashed, be careful!")
        vibrateOnCollision()
    }

    private fun toastOnCollision(text: String) {
        Toast
            .makeText(
                this,
                text,
                Toast.LENGTH_SHORT
            ).show()

    }

    private fun vibrateOnCollision() {
        val vibrator: Vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = this.getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            this.getSystemService(VIBRATOR_SERVICE) as Vibrator
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val oneShotVibratioEffect = VibrationEffect.createOneShot(
                100,
                VibrationEffect.DEFAULT_AMPLITUDE
            )
            vibrator.vibrate(oneShotVibratioEffect)

        } else {
            vibrator.vibrate(500)
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