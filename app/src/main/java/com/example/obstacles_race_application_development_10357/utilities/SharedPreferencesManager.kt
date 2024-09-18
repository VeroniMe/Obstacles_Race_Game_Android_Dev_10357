package com.example.obstacles_race_application_development_10357.utilities

import android.content.Context
import android.content.SharedPreferences

//Singlton
class SharedPreferencesManager private constructor (context: Context) {

    private val sharedPref: SharedPreferences = context.getSharedPreferences(Constants.DATA_KEY, Context.MODE_PRIVATE)

    companion object {  //static
        //double check - want to check if there is no one did not initilized it earlier
        //we will go to registeres  - volaetile. It gurantee that the object initilized once
        //add anotation - volatile
        @Volatile
        private var instance : SharedPreferencesManager? = null

        fun init( context: Context ) : SharedPreferencesManager {
            //double check
            return instance ?: synchronized(this) { //only one can use/touch to this part of code
                instance ?: SharedPreferencesManager(context).also { instance = it } //initilize
            }
        }

        fun getInstance() : SharedPreferencesManager {
            return instance ?: throw IllegalStateException("SharedPreferencesManager must be initialized by calling init(context) before use")
        }
    }
    fun putString(key:String, value: String) {
        val editor = sharedPref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key:String, defaultValue: String) : String {
        return sharedPref.getString(key, defaultValue) ?: defaultValue
    }

}