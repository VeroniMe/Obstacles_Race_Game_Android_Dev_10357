package com.example.obstacles_race_application_development_10357

import android.app.Application
import com.example.obstacles_race_application_development_10357.utilities.SharedPreferencesManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPreferencesManager.init(this)

    }
}