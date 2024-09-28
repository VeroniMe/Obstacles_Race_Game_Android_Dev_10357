package com.example.obstacles_race_application_development_10357.activities

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.example.obstacles_race_application_development_10357.R
import com.example.obstacles_race_application_development_10357.models.HighScoresList
import com.example.obstacles_race_application_development_10357.models.Score
import com.example.obstacles_race_application_development_10357.utilities.BackgroundMusicPlayer
import com.example.obstacles_race_application_development_10357.utilities.Constants
import com.example.obstacles_race_application_development_10357.utilities.DataManager
import com.example.obstacles_race_application_development_10357.utilities.PlayerLocation
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView



class GameOverActivity : AppCompatActivity() {


    private lateinit var game_over_IMG_bgr : AppCompatImageView
    private lateinit var game_over_LBL_text : MaterialTextView
    private lateinit var highScores_ET_name : TextInputEditText
    private lateinit var game_over_BTN_save_score : MaterialButton

    private lateinit var playerLocation : PlayerLocation
    private var score : Int = 0
    private var playerCount : Int = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)
        BackgroundMusicPlayer.getInstance().stopMusic()
        findViews()
        initViews()


    }

    private fun initViews() {
        val bundle : Bundle? = intent.extras
        score = bundle?.getInt(Constants.SCORE_KEY, 0)!!


        Glide
            .with(this)
            .load(R.drawable.game_over_bgr)
            .centerCrop()
            .placeholder(R.drawable.launcher_background)
            .into(game_over_IMG_bgr)


        this.playerLocation = PlayerLocation()
        playerLocation.init(this)



        game_over_BTN_save_score.setOnClickListener { view: View? ->
            playerLocation.getLastLocation(object : PlayerLocation.LocationCallback {
                override fun onLocationResult(location: Location) {
                    createScore(location)
                }

                override fun onLocationError(message: String) {
                    Log.e("GameOverActivity", message)
                }
            })

    }}


        private fun createScore(location: Location) {
            val playerName = highScores_ET_name.text?.toString()?.ifEmpty {
                "Player$playerCount"
            }

            // Build score
            val newScore = Score.Builder()
                .lat(location.latitude)
                .lng(location.longitude)
                .name(playerName ?: "Player$playerCount")
                .scorePoints(score)
                .build()

            val highScoreL: HighScoresList = DataManager.getHighScoresList()

            val updatedHighScoresList = HighScoresList.Builder(highScoreL.highScoresList)
                .addScore(newScore)
                .build()

            DataManager.saveHighScoresList(updatedHighScoresList)
            goToHighScores()
        }

    private fun goToHighScores() {
        val intent = Intent(this, ScoresActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun findViews() {
        game_over_IMG_bgr = findViewById(R.id.game_over_IMG_bgr)
        game_over_LBL_text = findViewById(R.id.game_over_LBL_text)
        highScores_ET_name = findViewById(R.id.highScores_ET_name)
        game_over_BTN_save_score = findViewById(R.id.game_over_BTN_save_score)

    }
}