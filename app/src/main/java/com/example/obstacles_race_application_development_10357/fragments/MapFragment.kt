package com.example.obstacles_race_application_development_10357.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.obstacles_race_application_development_10357.R
import com.google.android.material.textview.MaterialTextView


class MapFragment : Fragment() {

    private lateinit var map_LBL_title : MaterialTextView

    /*
        Fragment have another lifecycle
        Activity :          Fragment:
         - onCreate         - onAttach - connect fragment to activity
         - onStart          - onCreate - create activity
         - onResume         - onCreateView - before activity
         <<<Active!>>       - onActivityCreated
         - onPause          - onViewStateRestored
         - onStop           - onStart
         - onDestroy        - onResume --> <<Fragment Active!>> -> onPause -> onStop -> onDestroyView -> onDestroy -> onDetach
     */

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

}