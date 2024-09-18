package com.example.obstacles_race_application_development_10357.activities

import android.content.Intent
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
import com.example.obstacles_race_application_development_10357.R
import com.example.obstacles_race_application_development_10357.interfaces.Callback_MoveCallback
import com.example.obstacles_race_application_development_10357.logic.GameManager
import com.example.obstacles_race_application_development_10357.utilities.Constants
import com.example.obstacles_race_application_development_10357.utilities.GameMode
import com.example.obstacles_race_application_development_10357.utilities.MoveDetector
import com.example.obstacles_race_application_development_10357.utilities.Shapes
import com.example.obstacles_race_application_development_10357.utilities.SingleSoundPlayer
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

   private lateinit var main_LBL_score : MaterialTextView
   private lateinit var main_IMG_hearts : Array <ShapeableImageView>
   private lateinit var main_BTN_left : ExtendedFloatingActionButton
   //private lateinit var main_BTN_start : MaterialButton
   private lateinit var main_BTN_right : ExtendedFloatingActionButton
   private lateinit var main_IMG_asteroids : Array<Array<ShapeableImageView>>
   private lateinit var main_IMG_coins : Array<Array<ShapeableImageView>>
   private lateinit var main_IMG_ufos : Array<ShapeableImageView>
   private lateinit var gameManager: GameManager
   private lateinit var singleSoundPlayer : SingleSoundPlayer
   private lateinit var moveDetector: MoveDetector

   private var timerOn = false
   private var justStarted: Boolean = true
   private var collisions: Int = 0
   private var lastNewObsAppearance: Long = 0


   private lateinit var gameJob : Job
   private var score : Int = 0
   private var gameMode : GameMode = GameMode.BUTTONS
   private var gameSpeed : Long = Constants.LOW_SPEED
   private var obsAppearenceSpeed : Long = Constants.LOW_OBSTACLES_APPEARANCE



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews()
        singleSoundPlayer = SingleSoundPlayer(this)
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
        //moveDetector.stop()
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onStop() {
        super.onStop()
        stopTimer()
    }

    private fun initViews() {
        //intent is a previous activity
        val bundle : Bundle? = intent.extras
        val modeInt = bundle?.getInt(Constants.MODE_KEY, GameMode.BUTTONS.ordinal)
        gameMode = GameMode.entries[modeInt as Int]
        val highSpeed = bundle.getBoolean(Constants.HIGH_SPEED_KEY, false)
        if(gameMode == GameMode.SENSOR) {
            initMoveDetector()
            main_BTN_left.visibility = View.INVISIBLE

            main_BTN_right.visibility = View.INVISIBLE
            moveDetector.start()

        } else {
            //buttons
            if(highSpeed) {
                gameSpeed = Constants.HIGH_SPEED
                obsAppearenceSpeed = Constants.HIGH_OBSTACLES_APPEARANCE
            } else {
                gameSpeed = Constants.LOW_SPEED
                obsAppearenceSpeed = Constants.LOW_OBSTACLES_APPEARANCE
            }
        }

        main_LBL_score.text = score.toString()
        main_BTN_left.setOnClickListener { view: View? -> moveLeft()}
        main_BTN_right.setOnClickListener { view: View? -> moveRight()}
        obstaclesMove()

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

        main_LBL_score.text = gameManager.score.toString()
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
                singleSoundPlayer.playSound(R.raw.collision_sound)
                collisions++
            }

        }
        //refresh obstacles positions view
        for (i in gameManager.obstacles.indices) {
            for ( j in gameManager.obstacles[i].indices) {

                if(gameManager.obstacles[i][j] == Shapes.OBSTACLE) {
                    main_IMG_asteroids[i][j].visibility = View.VISIBLE
                    main_IMG_coins[i][j].visibility = View.INVISIBLE
                }
                else if(gameManager.obstacles[i][j] == Shapes.COIN) {
                    main_IMG_asteroids[i][j].visibility = View.INVISIBLE
                    main_IMG_coins[i][j].visibility = View.VISIBLE
                }
                else {
                    main_IMG_asteroids[i][j].visibility = View.INVISIBLE
                    main_IMG_coins[i][j].visibility = View.INVISIBLE
                }

            }
        }
        if(gameManager.isGameLost) {
            Log.d(
                "Game Status",
                "Game Over!"
            )  //d - debug, e -error, i - info, w - warning, t - trace
            //TODO: continue to implement + change screen
            changeActivity()
            //collisions = 0
            //gameManager.resetGameLogic()
            //main_IMG_hearts[0].visibility = View.VISIBLE
            //main_IMG_hearts[1].visibility = View.VISIBLE
            //main_IMG_hearts[2].visibility = View.VISIBLE
        }

    }

    //GAME FLOW
    private fun obstaclesMove(){


        Log.d(
            "POSITION",
            "OBSTACLES MOVE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
        )
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
                    if(currentTime - lastNewObsAppearance > obsAppearenceSpeed) {
                        newObstacles = true
                        lastNewObsAppearance = currentTime
                    }
                    gameManager.moveObstacles(newObstacles) //calculate new positions
                    refreshUI()
                    delay(gameSpeed)
                }
            }
        }

    }

    private fun toastAndVibrateOnCollision() {
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

    private fun changeActivity() {
        val intent = Intent(this, GameOverActivity::class.java)
        var bundle = Bundle()
        bundle.putInt(Constants.SCORE_KEY, score)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }

    private fun findViews() {
        main_LBL_score = findViewById(R.id.main_LBL_score)
        main_BTN_left = findViewById(R.id.main_BTN_left)
        //main_BTN_start = findViewById(R.id.main_BTN_start)
        main_BTN_right = findViewById(R.id.main_BTN_right)
        main_IMG_hearts = arrayOf(
            findViewById(R.id.main_IMG_heart1),
            findViewById(R.id.main_IMG_heart2),
            findViewById(R.id.main_IMG_heart3)
        )
        main_IMG_ufos = arrayOf(
            findViewById(R.id.main_IMG_ufo0),
            findViewById(R.id.main_IMG_ufo1),
            findViewById(R.id.main_IMG_ufo2),
            findViewById(R.id.main_IMG_ufo3),
            findViewById(R.id.main_IMG_ufo4)
        )
        main_IMG_asteroids = arrayOf(
            arrayOf(
                findViewById(R.id.main_IMG_asteroid00),
                findViewById(R.id.main_IMG_asteroid01),
                findViewById(R.id.main_IMG_asteroid02),
                findViewById(R.id.main_IMG_asteroid03),
                findViewById(R.id.main_IMG_asteroid04)
            ),
            arrayOf(
                findViewById(R.id.main_IMG_asteroid10),
                findViewById(R.id.main_IMG_asteroid11),
                findViewById(R.id.main_IMG_asteroid12),
                findViewById(R.id.main_IMG_asteroid13),
                findViewById(R.id.main_IMG_asteroid14)
            ),
            arrayOf(
                findViewById(R.id.main_IMG_asteroid20),
                findViewById(R.id.main_IMG_asteroid21),
                findViewById(R.id.main_IMG_asteroid22),
                findViewById(R.id.main_IMG_asteroid23),
                findViewById(R.id.main_IMG_asteroid24)
            ),
            arrayOf(
                findViewById(R.id.main_IMG_asteroid30),
                findViewById(R.id.main_IMG_asteroid31),
                findViewById(R.id.main_IMG_asteroid32),
                findViewById(R.id.main_IMG_asteroid33),
                findViewById(R.id.main_IMG_asteroid34)
            ),
            arrayOf(
                findViewById(R.id.main_IMG_asteroid40),
                findViewById(R.id.main_IMG_asteroid41),
                findViewById(R.id.main_IMG_asteroid42),
                findViewById(R.id.main_IMG_asteroid43),
                findViewById(R.id.main_IMG_asteroid44)
            ),
            arrayOf(
                findViewById(R.id.main_IMG_asteroid50),
                findViewById(R.id.main_IMG_asteroid51),
                findViewById(R.id.main_IMG_asteroid52),
                findViewById(R.id.main_IMG_asteroid53),
                findViewById(R.id.main_IMG_asteroid54)
            ),
            arrayOf(
                findViewById(R.id.main_IMG_asteroid60),
                findViewById(R.id.main_IMG_asteroid61),
                findViewById(R.id.main_IMG_asteroid62),
                findViewById(R.id.main_IMG_asteroid63),
                findViewById(R.id.main_IMG_asteroid64)
            ),
            arrayOf(
                findViewById(R.id.main_IMG_asteroid70),
                findViewById(R.id.main_IMG_asteroid71),
                findViewById(R.id.main_IMG_asteroid72),
                findViewById(R.id.main_IMG_asteroid73),
                findViewById(R.id.main_IMG_asteroid74)
            ),



        )

        main_IMG_coins = arrayOf(
            arrayOf(
                findViewById(R.id.main_IMG_crystal00),
                findViewById(R.id.main_IMG_crystal01),
                findViewById(R.id.main_IMG_crystal02),
                findViewById(R.id.main_IMG_crystal03),
                findViewById(R.id.main_IMG_crystal04)
            ),
            arrayOf(
                findViewById(R.id.main_IMG_crystal10),
                findViewById(R.id.main_IMG_crystal11),
                findViewById(R.id.main_IMG_crystal12),
                findViewById(R.id.main_IMG_crystal13),
                findViewById(R.id.main_IMG_crystal14)
            ),
            arrayOf(
                findViewById(R.id.main_IMG_crystal20),
                findViewById(R.id.main_IMG_crystal21),
                findViewById(R.id.main_IMG_crystal22),
                findViewById(R.id.main_IMG_crystal23),
                findViewById(R.id.main_IMG_crystal24)
            ),
            arrayOf(
                findViewById(R.id.main_IMG_crystal30),
                findViewById(R.id.main_IMG_crystal31),
                findViewById(R.id.main_IMG_crystal32),
                findViewById(R.id.main_IMG_crystal33),
                findViewById(R.id.main_IMG_crystal34)
            ),
            arrayOf(
                findViewById(R.id.main_IMG_crystal40),
                findViewById(R.id.main_IMG_crystal41),
                findViewById(R.id.main_IMG_crystal42),
                findViewById(R.id.main_IMG_crystal43),
                findViewById(R.id.main_IMG_crystal44)
            ),
            arrayOf(
                findViewById(R.id.main_IMG_crystal50),
                findViewById(R.id.main_IMG_crystal51),
                findViewById(R.id.main_IMG_crystal52),
                findViewById(R.id.main_IMG_crystal53),
                findViewById(R.id.main_IMG_crystal54)
            ),
            arrayOf(
                findViewById(R.id.main_IMG_crystal60),
                findViewById(R.id.main_IMG_crystal61),
                findViewById(R.id.main_IMG_crystal62),
                findViewById(R.id.main_IMG_crystal63),
                findViewById(R.id.main_IMG_crystal64)
            ),
            arrayOf(
                findViewById(R.id.main_IMG_crystal70),
                findViewById(R.id.main_IMG_crystal71),
                findViewById(R.id.main_IMG_crystal72),
                findViewById(R.id.main_IMG_crystal73),
                findViewById(R.id.main_IMG_crystal74)
            ),



            )
    }


}