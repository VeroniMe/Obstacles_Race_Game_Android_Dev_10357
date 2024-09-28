package com.example.obstacles_race_application_development_10357.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.example.obstacles_race_application_development_10357.R
import com.example.obstacles_race_application_development_10357.utilities.BackgroundMusicPlayer
import com.example.obstacles_race_application_development_10357.utilities.Constants
import com.example.obstacles_race_application_development_10357.utilities.GameMode
import com.google.android.material.button.MaterialButton


class MenuActivity : AppCompatActivity() {

    private lateinit var menu_IMG_background : AppCompatImageView

    private lateinit var radio_BTN_btnmode : RadioButton

    private lateinit var radio_BTN_highSpeed : RadioButton

    private lateinit var radio_BTN_sensormode : RadioButton
    private lateinit var radio_GRP_mode : RadioGroup

    private lateinit var menu_LBL_speed : TextView
    private lateinit var radio_GRP_menu_speed : RadioGroup

    private lateinit var main_BTN_start : MaterialButton
    private lateinit var main_BTN_scores : MaterialButton



    private var mode: GameMode = GameMode.BUTTONS
    private var highSpeed: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_menu)
        findViews()
        initViews()
        BackgroundMusicPlayer.getInstance().startMusic()
    }


    private fun initViews() {
       Glide
           .with(this)
           .load(R.drawable.space_race_intro)
           .centerCrop()
           .placeholder(R.drawable.launcher_background)
           .into(menu_IMG_background)

        main_BTN_start.setOnClickListener { view: View? -> startGame()}
        main_BTN_scores.setOnClickListener {view: View? -> goToHighScores()}


        radio_BTN_sensormode.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                menu_LBL_speed.visibility = View.INVISIBLE
                radio_GRP_menu_speed.visibility = View.INVISIBLE
            }
        }

        radio_BTN_btnmode.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                menu_LBL_speed.visibility = View.VISIBLE
                radio_GRP_menu_speed.visibility = View.VISIBLE
            }
        }

    }

    private fun goToHighScores() {
        val intent = Intent(this, ScoresActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startGame() {

        val selectedRadioButtonId =  radio_GRP_mode.checkedRadioButtonId
        if (selectedRadioButtonId != -1 && selectedRadioButtonId == R.id.radio_BTN_sensormode)
            mode = GameMode.SENSOR

        val selectedRadioButtonId2 =  radio_GRP_menu_speed.checkedRadioButtonId
        if (selectedRadioButtonId2 != -1 && selectedRadioButtonId2 == R.id.radio_BTN_highSpeed)
            highSpeed = true

        changeActivity()
    }

    private fun changeActivity() {
        val intent = Intent(this, MainActivity::class.java)
        var bundle = Bundle()
        bundle.putInt(Constants.MODE_KEY, mode.ordinal)
        bundle.putBoolean(Constants.HIGH_SPEED_KEY, highSpeed)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }

    private fun findViews() {
        menu_IMG_background = findViewById(R.id.menu_IMG_background)
        radio_GRP_mode = findViewById(R.id.radio_GRP_mode)
        radio_GRP_menu_speed = findViewById(R.id.radio_GRP_menu_speed)
        main_BTN_start = findViewById(R.id.main_BTN_start)
        main_BTN_scores = findViewById(R.id.main_BTN_scores)
        radio_BTN_btnmode = findViewById(R.id.radio_BTN_btnmode)
        radio_BTN_highSpeed = findViewById(R.id.radio_BTN_highSpeed)
        radio_BTN_sensormode = findViewById(R.id.radio_BTN_sensormode)
        menu_LBL_speed = findViewById(R.id.menu_LBL_speed)

    }
    companion object {

    }
}