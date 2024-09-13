package com.example.obstacles_race_application_development_10357.utilities

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.example.obstacles_race_application_development_10357.interfaces.Callback_MoveCallback
import kotlin.math.abs

class MoveDetector(context : Context, private val moveCallback: Callback_MoveCallback?) {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) as Sensor
    private lateinit var sensorEventListener : SensorEventListener

    var timeStamp: Long = 0L

    //when we run constructor, this what it do - init()
    init {
        initEventListener()
    }

    private fun initEventListener() {
        sensorEventListener = object:SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val x = event.values[0]
                val y = event.values[1]
                calculateMove(x, y)

            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                //NOT RELEVANT - no changes in accuracy in accelerometer
            }

        }
    }

    private fun calculateMove(x: Float, y: Float) {
        if(System.currentTimeMillis() - timeStamp >= 200) {
            timeStamp = System.currentTimeMillis()

            if(x <= -1.0) {
                moveCallback?.moveRightCall()
            }

            if(x >= 1.0) {
                moveCallback?.moveLeftCall()
            }

            if(x >= 0.05 && x <= -0.05) {
                moveCallback?.centralize()
            }




        }
    }

    fun start() {
       sensorManager.registerListener(
           sensorEventListener,
           sensor,
           SensorManager.SENSOR_DELAY_GAME
       )
    }

    fun stop() {
        sensorManager.unregisterListener(
            sensorEventListener,
            sensor
        )
    }
}


