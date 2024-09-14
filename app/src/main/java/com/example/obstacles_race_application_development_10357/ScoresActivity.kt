package com.example.obstacles_race_application_development_10357

import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ScoresActivity : AppCompatActivity() {


    private lateinit var main_FRAME_list : FrameLayout
    private lateinit var main_FRAME_map : FrameLayout


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
        TODO("Not yet implemented")
    }
}