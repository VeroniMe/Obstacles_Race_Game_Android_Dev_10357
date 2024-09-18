package com.example.obstacles_race_application_development_10357.utilities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class PlayerLocation  {

    private val FINE_PERMISSION_CODE : Int = 1
    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    private var context:Context? = null
    lateinit var currentLocation : Location
        private set

    fun init( context: Context ) {
        this.context = context
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        getLastLocation()

    }

    private fun getLastLocation() {
        if (context == null) return
        if (ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            (context as? Activity)?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    FINE_PERMISSION_CODE
                )
            }
            return
        }
        fusedLocationProviderClient
            .lastLocation
            .addOnSuccessListener {
                    location -> if(location != null) {
                        currentLocation = location
                }
            }
            .addOnFailureListener{ }
    }
}