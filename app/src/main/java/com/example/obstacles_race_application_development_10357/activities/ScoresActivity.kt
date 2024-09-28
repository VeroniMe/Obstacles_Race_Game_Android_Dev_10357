package com.example.obstacles_race_application_development_10357.activities

import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.obstacles_race_application_development_10357.R
import com.example.obstacles_race_application_development_10357.fragments.HighScoreFragment
import com.example.obstacles_race_application_development_10357.fragments.MapFragment
import com.example.obstacles_race_application_development_10357.interfaces.Callback_HighScoreItemClicked

class ScoresActivity : AppCompatActivity(){


    private lateinit var main_FRAME_list : FrameLayout
    private lateinit var main_FRAME_map : FrameLayout
    private lateinit var highScoreFragment : HighScoreFragment
    private lateinit var mapFragment: MapFragment




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scores)
        findViews()
        initViews()

    }

    private fun findViews() {
        main_FRAME_list = findViewById(R.id.main_FRAME_list)
        main_FRAME_map = findViewById(R.id.main_FRAME_map)

    }

    private fun initViews() {
        Log.d("### START SCORE ACT", "---------------------------------------------")
        highScoreFragment = HighScoreFragment()
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


}