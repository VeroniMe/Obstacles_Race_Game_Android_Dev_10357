package com.example.obstacles_race_application_development_10357

import android.app.Application
import com.example.obstacles_race_application_development_10357.utilities.BackgroundMusicPlayer
import com.example.obstacles_race_application_development_10357.utilities.DataManager
import com.example.obstacles_race_application_development_10357.utilities.SharedPreferencesManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPreferencesManager.init(this)
        DataManager.init(this)
        DataManager.loadHighScoresList()
        BackgroundMusicPlayer.init(this)
        BackgroundMusicPlayer.getInstance().setResourceId(R.raw.background_music)
        BackgroundMusicPlayer.getInstance().startMusic()
    }



}