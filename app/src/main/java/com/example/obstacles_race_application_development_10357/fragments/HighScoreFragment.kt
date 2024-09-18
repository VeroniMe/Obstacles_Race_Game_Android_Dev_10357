package com.example.obstacles_race_application_development_10357.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.obstacles_race_application_development_10357.R
import com.example.obstacles_race_application_development_10357.interfaces.Callback_HighScoreItemClicked
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText


class HighScoreFragment : Fragment() {


    private lateinit var highScores_ET_name: TextInputEditText
    private lateinit var highScores_BTN_send: MaterialButton
    var callbackHighScoreItemClicked: Callback_HighScoreItemClicked? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_high_score, container, false)
        findViews(v)
        iniViews(v)
        return v
    }

    private fun iniViews(v: View) {
        highScores_BTN_send.setOnClickListener{v:View ->
            var coords = highScores_ET_name.text?.split(",")
            var lat:Double = coords?.get(0)?.toDouble() ?:0.0
            var lng:Double = coords?.get(1)?.toDouble() ?:0.0
            itemClicked(lat, lng)
        }
    }

    private fun itemClicked(lat: Double, lng: Double) {
        callbackHighScoreItemClicked?.highScoreItemClicked(lat, lng) //activate in activity
    }

    private fun findViews(v:View) {
        highScores_ET_name = v.findViewById(R.id.highScores_ET_name)
        highScores_BTN_send = v.findViewById(R.id.highScores_BTN_send)
    }


}