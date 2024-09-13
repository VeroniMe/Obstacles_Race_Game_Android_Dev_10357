package com.example.obstacles_race_application_development_10357

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide


class MenuActivity : AppCompatActivity() {

    private lateinit var menu_IMG_background : AppCompatImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_menu)
        findViews()
        initViews()
    }


    private fun initViews() {
       Glide
           .with(this)
           .load(R.drawable.space_race_intro)
           .centerCrop()
           .placeholder(R.drawable.launcher_background)
           .into(menu_IMG_background)
    }

    private fun findViews() {
        menu_IMG_background = findViewById(R.id.menu_IMG_background)
    }
}