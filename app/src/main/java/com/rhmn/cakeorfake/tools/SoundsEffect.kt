package com.rhmn.cakeorfake.tools

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.view.View
import com.rhmn.cakeorfake.R

class SoundsEffect {

    companion object{
        fun playClick(context: Context){
            val sharedPrefHandler = SharedPrefHandler(context)
            if(sharedPrefHandler.getSoundEffect()) {
                val mp: MediaPlayer = MediaPlayer.create(context, R.raw.click)
                mp.start()
            }

        }

        fun playIntro(context: Context){
            val mp: MediaPlayer = MediaPlayer.create(context, R.raw.intro)
            mp.start()

        }

        fun play(context: Context, res: Int){
            val mp: MediaPlayer = MediaPlayer.create(context, res)
            mp.start()

        }
    }
}