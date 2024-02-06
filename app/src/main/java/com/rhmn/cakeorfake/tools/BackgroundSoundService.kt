package com.rhmn.cakeorfake.tools

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.widget.Toast
import androidx.annotation.Nullable
import com.rhmn.cakeorfake.R


class BackgroundSoundService : Service() {

    var mediaPlayer: MediaPlayer? = null

    @Nullable
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.sound)
        mediaPlayer!!.isLooping = true // Set looping
        mediaPlayer!!.setVolume(100f, 100f)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer!!.start()
//        Toast.makeText(
//            applicationContext,
//            "Playing Bohemian Rashpody in the Background",
//            Toast.LENGTH_SHORT
//        ).show()
        return startId
    }

    override fun onStart(intent: Intent?, startId: Int) {}
    override fun onDestroy() {
        mediaPlayer!!.stop()
        mediaPlayer!!.release()
    }
    override fun onLowMemory() {
        mediaPlayer!!.stop()
        mediaPlayer!!.release()
    }


}