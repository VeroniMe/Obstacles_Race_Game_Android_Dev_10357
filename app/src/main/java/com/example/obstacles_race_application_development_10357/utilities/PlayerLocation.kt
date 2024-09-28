package com.example.obstacles_race_application_development_10357.utilities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

/*class PlayerLocation  {

    private val FINE_PERMISSION_CODE : Int = 1
    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    private var context:Context? = null

    private var currentLocation : Location = Location("").apply {
        this.latitude = 0.0
        this.longitude = 0.0
    }
        private set

    fun init( context: Context ) {
        this.context = context
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        getLastLocation()

    }

    fun getCurrentLocation(): Location {
        return currentLocation
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
            .addOnSuccessListener { location ->
                if(location != null) {
                        currentLocation = location
                        Log.d("PlayerLocation", "Location obtained: $currentLocation")
                } else {
                    Log.d("PlayerLocation", "No location available")
                }
            }
            .addOnFailureListener{
                    exception ->
                Log.e("PlayerLocation", "Failed to get location: ${exception.message}")
            }
    }
} */

//com.example.obstacles_race_application_development_10357

//VER2
class PlayerLocation {

    interface LocationCallback {
        fun onLocationResult(location: Location)
        fun onLocationError(message: String)
    }


    private val FINE_PERMISSION_CODE: Int = 1
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var context: Context? = null

   // new  private var currentLocation: Location = Location("").apply {
   // new      latitude = 0.0
   // new      longitude = 0.0
   // new  }
   // new      private set

    fun init(context: Context) {
        this.context = context
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        // new getLastLocation()
    }

   //new fun getCurrentLocation(): Location {
   //new     return currentLocation
   //new }

   //new  fun refreshCurrentLocation() {
   //new      getLastLocation()
   //new  }

    fun getLastLocation(callback: LocationCallback) {
        if (context == null) return

        if (ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
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
            .addOnSuccessListener { location ->
                if (location != null) {
                    callback.onLocationResult(location)
                    Log.d("PlayerLocation", "Location obtained: $location")
                } else {
                    callback.onLocationError("No location available")
                    Log.d("PlayerLocation", "No location available")
                }
            }
            .addOnFailureListener { exception ->
                callback.onLocationError("Failed to get location: ${exception.message}")
                Log.e("PlayerLocation", "Failed to get location: ${exception.message}")
            }
    }

        //new context?.let { ctx ->
//new
        //new     if (ActivityCompat.checkSelfPermission(
        //new             ctx,
        //new             Manifest.permission.ACCESS_FINE_LOCATION
        //new         ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        //new             ctx,
        //new             Manifest.permission.ACCESS_COARSE_LOCATION
        //new         ) != PackageManager.PERMISSION_GRANTED
        //new     ) {
        //new         // Request permissions if not granted
        //new         (ctx as? Activity)?.let {
        //new             ActivityCompat.requestPermissions(
        //new                 it,
        //new                 arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
        //new                 FINE_PERMISSION_CODE
        //new             )
        //new         }
        //new         return@getLastLocation
        //new     }
//new
        //new     fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
        //new         if (location != null) {
        //new             currentLocation = location
        //new             Log.d("PlayerLocation", "Location obtained: $currentLocation")
        //new         } else {
        //new             Log.d("PlayerLocation", "No location available")
        //new         }
        //new     }.addOnFailureListener { exception ->
        //new         Log.e("PlayerLocation", "Failed to get location: ${exception.message}")
        //new     }
        //new }
}





