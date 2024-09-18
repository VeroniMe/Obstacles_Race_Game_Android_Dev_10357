package com.example.obstacles_race_application_development_10357.activities

import android.content.SharedPreferences
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.obstacles_race_application_development_10357.R
import com.example.obstacles_race_application_development_10357.models.HighScoresList
import com.example.obstacles_race_application_development_10357.models.Score
import com.example.obstacles_race_application_development_10357.utilities.Constants
import com.example.obstacles_race_application_development_10357.utilities.DataManager
import com.example.obstacles_race_application_development_10357.utilities.GameMode
import com.example.obstacles_race_application_development_10357.utilities.PlayerLocation
import com.example.obstacles_race_application_development_10357.utilities.SharedPreferencesManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.google.gson.Gson

class GameOverActivity : AppCompatActivity() {


    private lateinit var game_over_LBL_text : MaterialTextView
    private lateinit var highScores_ET_name : TextInputEditText
    private lateinit var game_over_BTN_save_score : MaterialButton

    private lateinit var playerLocation : PlayerLocation
    private var playerCount : Int = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)
        findViews()
        initViews()


    }

    private fun initViews() {
        //TODO: here need to do putString to SharedPreferences
        game_over_BTN_save_score.setOnClickListener { view: View? -> createScore()}
        this.playerLocation = PlayerLocation()

        val highScoreList : HighScoresList = DataManager.generateHighScoreList()
        val gson = Gson()
        val highScoreListAsJson : String = gson.toJson(highScoreList)
        Log.d("highScoreListAsJson", "highScoreListAsJson : " + highScoreListAsJson)


        //SharedPreferencesManager.putString(this, Constants.HIGH_SCORE_LIST_KEY, highScoreListAsJson)
        //val fromShPref = SharedPreferencesManager.getString(this, Constants.HIGH_SCORE_LIST_KEY, highScoreListAsJson)

        //ver2
        //val shP2 : SharedPreferencesManager = SharedPreferencesManager(this)

        //CREATE DATA LAST VERSION - save data
        SharedPreferencesManager
            .getInstance()
            .putString(Constants.HIGH_SCORE_LIST_KEY, highScoreListAsJson)

        val  highScoresListSP = SharedPreferencesManager
            .getInstance()
            .getString(Constants.HIGH_SCORE_LIST_KEY, " ")

        val scoresListObjFromSP:HighScoresList = gson.fromJson(highScoresListSP, HighScoresList::class.java)
        //delete shared preferences = tools->ADB Idea -> clear app data


    }

    private fun createScore() {
        /*
        * We need to create score here:
        * 1) get name from the EditText cell - V
        * 2) get coordinates from google map
        * 3) we need to get score from previous activity - V
        * 4) add this to our high scores list
        * */
        val textInputEditText = findViewById<TextInputEditText>(R.id.highScores_ET_name)
        val playerName = textInputEditText?.text?.toString()?.ifEmpty {
            "Player$playerCount" // Use the player count in the default name
            playerCount++
        }
        val bundle : Bundle? = intent.extras
        val scoreInt = bundle?.getInt(Constants.SCORE_KEY, 0)
        var currentLocation = playerLocation.currentLocation
        //build score
       var newScore = Score.Builder()
                                    .name(playerName as String)
                                    .scorePoints(scoreInt as Int)
                                    .lan(currentLocation.latitude)
                                    .lng(currentLocation.longitude)
                                    .build()


       }

    private fun findViews() {
        game_over_LBL_text = findViewById(R.id.game_over_LBL_text)
        highScores_ET_name = findViewById(R.id.highScores_ET_name)
        game_over_BTN_save_score = findViewById(R.id.game_over_BTN_save_score)
    }
}