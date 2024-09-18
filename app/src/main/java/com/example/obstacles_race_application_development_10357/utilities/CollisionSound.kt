package com.example.obstacles_race_application_development_10357.utilities

import android.content.Context
import android.media.MediaPlayer
import android.provider.MediaStore.Audio.Media
import java.lang.ref.WeakReference

class CollisionSound private constructor(context: Context){


    private val contexRef = WeakReference(context)
    private var mediaPlayer: MediaPlayer? = null
    private var RES_ID: Int = 0

    fun setResourseId(id:Int) {
        this.RES_ID = id
        initMediaPlayer()
    }
    private fun initMediaPlayer() {
        if(mediaPlayer != null)
            release()
        mediaPlayer=MediaPlayer.create(contexRef.get(), RES_ID)
        mediaPlayer!!.isLooping = true
        mediaPlayer!!.setVolume(0.4F, 0.4F)

    }
    private fun release() {
       if(mediaPlayer==null)
           return
       try{
           mediaPlayer!!.release() //!!=I know that mediaPlayer exist
           mediaPlayer = null
       } catch (e:IllegalStateException) {
           e.printStackTrace()

       }
    }

    fun startSound() {
        if(mediaPlayer==null || mediaPlayer!!.isPlaying)
            initMediaPlayer()
        try{
            mediaPlayer!!.start()
        } catch (e:IllegalStateException){
            e.printStackTrace()
        }
    }

    fun pauseSound() {

    }
    fun stopSound() {

    }
}