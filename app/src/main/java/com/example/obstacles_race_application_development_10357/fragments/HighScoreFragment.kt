package com.example.obstacles_race_application_development_10357.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.obstacles_race_application_development_10357.R
import com.example.obstacles_race_application_development_10357.adapters.HighScoreAdapter
import com.example.obstacles_race_application_development_10357.interfaces.Callback_HighScoreItemClicked
import com.example.obstacles_race_application_development_10357.utilities.DataManager


class HighScoreFragment : Fragment() {

    var callbackHighScoreItemClicked: Callback_HighScoreItemClicked? = null
    private lateinit var highScoreAdapter: HighScoreAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_high_score, container, false)
        findViews(v)
        val highScoresList = DataManager.getHighScoresList()
        highScoreAdapter = HighScoreAdapter(highScoresList) { score ->
            // Handle item click to focus on map
            itemClicked(score.lat, score.lng)
        }
        recyclerView.adapter = highScoreAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        iniViews(v)
        return v
    }

    private fun iniViews(v: View) {

    }

    private fun itemClicked(lat: Double, lng: Double) {
        callbackHighScoreItemClicked?.highScoreItemClicked(lat, lng) //activate in activity
    }

    private fun findViews(view:View) {
        recyclerView = view.findViewById(R.id.recyclerViewScores)
    }


}