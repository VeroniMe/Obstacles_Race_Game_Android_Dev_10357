package com.example.obstacles_race_application_development_10357.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.obstacles_race_application_development_10357.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.textview.MaterialTextView


class MapFragment : Fragment(), OnMapReadyCallback {
    private  var mapFragment : SupportMapFragment? = null
    private  var mMap: GoogleMap? = null


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
    ): View {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_map, container, false)
        findViews(v)
        initViews()
        return v
    }

    private fun initViews() {
        try {
            mapFragment?.getMapAsync(this)
        } catch (ex : Exception) {
            ex.printStackTrace()
        }
    }

    private fun findViews(v : View) {
        mapFragment = childFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment?
    }

    public fun zoom(lat : Double, lng : Double) {

    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.mMap = googleMap


        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap!!.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(sydney))


        this.mMap?.clear()
    }

}