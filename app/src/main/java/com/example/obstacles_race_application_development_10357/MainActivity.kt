package com.example.obstacles_race_application_development_10357

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.imageview.ShapeableImageView

class MainActivity : AppCompatActivity() {


   private lateinit var main_IMG_hearts : Array <ShapeableImageView>
   private lateinit var main_BTN_left : ExtendedFloatingActionButton
   private lateinit var main_BTN_start : MaterialButton
   private lateinit var main_BTN_right : ExtendedFloatingActionButton



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews()
        initViews()
    }

    private fun initViews() {
        //main_BTN_start
        main_BTN_left.setOnClickListener { view: View? -> moveLeft()}
        main_BTN_right.setOnClickListener { view: View? -> moveRight()}
    }

    private fun findViews() {
        main_IMG_hearts = arrayOf(
            findViewById(R.id.main_IMG_heart1),
            findViewById(R.id.main_IMG_heart1),
            findViewById(R.id.main_IMG_heart1)
        )
        main_BTN_left = findViewById(R.id.main_BTN_left)
        main_BTN_start = findViewById(R.id.main_BTN_start)
        main_BTN_right = findViewById(R.id.main_BTN_right)
    }
}