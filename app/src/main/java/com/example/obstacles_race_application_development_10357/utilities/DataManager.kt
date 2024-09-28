package com.example.obstacles_race_application_development_10357.utilities

import android.content.Context
import android.util.Log
import com.example.obstacles_race_application_development_10357.models.HighScoresList
import com.example.obstacles_race_application_development_10357.models.Score
import com.google.gson.Gson

//object DataManager {
//
//    fun generateHighScoreList() : HighScoresList {
//
//       //load existed list of scores from SP
//
//        return HighScoresList
//            .Builder()
//            .addScore(
//                Score
//                    .Builder()
//                    .name("Veronika")
//                    .scorePoints(10)
//                    .lan(32.0)
//                    .lng(34.0)
//                    .build()
//            )
//            .addScore(
//                Score
//                    .Builder()
//                    .name("Bogdan")
//                    .scorePoints(15)
//                    .lan(32.1)
//                    .lng(34.0)
//                    .build()
//            ).build()
//    }
//}

//Singleton
class DataManager private constructor (context: Context) {

    companion object {  //static
        //double check - want to check if there is no one did not initialized it earlier
        //we will go to registers  - Volatile. It guarantee that the object initialized once
        //add annotation - volatile
        @Volatile
        private var instance : DataManager? = null
        private lateinit var highScoresList: HighScoresList
            private set

        fun init( context: Context) : DataManager {
            //double check
            val ins =  instance ?: synchronized(this) { //only one can use/touch to this part of code
                instance ?: DataManager(context).also { instance = it } //initilize
            }
            //loadHighScoresList()
            return ins
        }

        fun getInstance() : DataManager {
            return instance ?: throw IllegalStateException("DataManager must be initialized by calling init(context) before use")
        }

        fun getHighScoresList(): HighScoresList {
            if (!::highScoresList.isInitialized) {
                throw IllegalStateException("HighScoresList is not initialized. Call init(context) first.")
            }
            return highScoresList
        }

        fun loadHighScoresList() {
            val gson = Gson()
            val highScoresListFromSP = SharedPreferencesManager
                .getInstance()
                .getString(Constants.HIGH_SCORE_LIST_KEY, "")
            Log.d("LOAD* highScoresListFromSP", "highScoresListFromSP: " + highScoresListFromSP)

            //val highScoresListObjFromSP: HighScoresList = gson.fromJson(highScoresListFromSP, HighScoresList::class.java)
            if (highScoresListFromSP.isEmpty()) {
                highScoresList = HighScoresList.Builder().build()
                Log.d("highScoresList", "Initialized with an empty HighScoresList")
            } else {
                highScoresList = gson.fromJson(highScoresListFromSP, HighScoresList::class.java)
                Log.d("highScoresListObjFromSP", "highScoresListObjFromSP: " + highScoresList)
            }
            //now we loaded list of high scores of the game from shared preferences

        }

        fun saveHighScoresList(updatedHighScoresList: HighScoresList) {
            this.highScoresList = updatedHighScoresList
            Log.d("DataMng scoresList", "scoresList: " + this.highScoresList)
            val gson = Gson()
            val highScoresAsJson: String = gson.toJson(this.highScoresList)
            Log.d("SAVE* highScoresAsJson", "highScoresAsJson: " + highScoresAsJson)
            SharedPreferencesManager
                .getInstance()
                .putString(Constants.HIGH_SCORE_LIST_KEY, highScoresAsJson)

           // val test = 50
           // val testJSON:String = gson.toJson(test)
           // SharedPreferencesManager
           //     .getInstance()
           //     .putString(Constants.HIGH_SCORE_LIST_KEY, "")

            //var hlist = HighScoresList.Builder()
            //    .addScore(
            //        Score.Builder()
            //            .lat(13.0)
            //            .lng(15.0)
            //            .name("kuku")
            //            .scorePoints(13)
            //            .build()
            //    )
            //    .addScore(
            //        Score.Builder()
            //            .lat(12.0)
            //            .lng(125.0)
            //            .name("gogo")
            //            .scorePoints(14)
            //            .build()
            //    )
//
            //val testList:String = gson.toJson(hlist)
            //SharedPreferencesManager.getInstance().putString("test_list", testList)
            //val testValue = SharedPreferencesManager.getInstance().getString("test_list", "")
            //Log.d("Test", "Saved value: $testValue")  // Should log "test_value"



        }




    }




}