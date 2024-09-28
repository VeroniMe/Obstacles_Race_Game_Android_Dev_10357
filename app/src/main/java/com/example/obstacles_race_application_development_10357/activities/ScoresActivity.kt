package com.example.obstacles_race_application_development_10357.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.obstacles_race_application_development_10357.R
import com.example.obstacles_race_application_development_10357.fragments.HighScoreFragment
import com.example.obstacles_race_application_development_10357.fragments.MapFragment
import com.example.obstacles_race_application_development_10357.interfaces.Callback_HighScoreItemClicked
import com.example.obstacles_race_application_development_10357.utilities.Constants
import com.google.android.material.button.MaterialButton

class ScoresActivity : AppCompatActivity(){


    private lateinit var main_FRAME_list : FrameLayout
    private lateinit var main_FRAME_map : FrameLayout
    private lateinit var highScoreFragment : HighScoreFragment
    private lateinit var mapFragment: MapFragment
    private lateinit var scores_BTN_return_to_menu : MaterialButton




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scores)
        findViews()
        initViews()

    }

    private fun findViews() {
        main_FRAME_list = findViewById(R.id.main_FRAME_list)
        main_FRAME_map = findViewById(R.id.main_FRAME_map)
        scores_BTN_return_to_menu = findViewById(R.id.scores_BTN_return_to_menu)

    }

    private fun initViews() {
        Log.d("### START SCORE ACT", "---------------------------------------------")
        highScoreFragment = HighScoreFragment()
        scores_BTN_return_to_menu.setOnClickListener{ view: View? -> returnToMenu()}
        highScoreFragment.callbackHighScoreItemClicked = object : Callback_HighScoreItemClicked {
            override fun highScoreItemClicked(lat: Double, lng: Double) {
                mapFragment.zoom(lat, lng)
            }

        }
        supportFragmentManager.beginTransaction()
            .add(R.id.main_FRAME_list, highScoreFragment) //transaction of fragment
            .commit()


        mapFragment = MapFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.main_FRAME_map, mapFragment) //transaction of fragment
            .commit()


    }

    private fun returnToMenu() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }


}