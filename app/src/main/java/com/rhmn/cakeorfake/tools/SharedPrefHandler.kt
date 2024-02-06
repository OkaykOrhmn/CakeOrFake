package com.rhmn.cakeorfake.tools

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.rhmn.cakeorfake.tools.SharedPrefHandler.Constants.Companion.FIRST_TIME_NAME
import com.rhmn.cakeorfake.tools.SharedPrefHandler.Constants.Companion.GAME_BACK_SOUND
import com.rhmn.cakeorfake.tools.SharedPrefHandler.Constants.Companion.GAME_COIN_NAME
import com.rhmn.cakeorfake.tools.SharedPrefHandler.Constants.Companion.GAME_EFFECT_SOUND
import com.rhmn.cakeorfake.tools.SharedPrefHandler.Constants.Companion.GAME_LEVEL_NAME
import com.rhmn.cakeorfake.tools.SharedPrefHandler.Constants.Companion.GAME_NAME
import com.rhmn.cakeorfake.tools.SharedPrefHandler.Constants.Companion.GAME_VOICE_SOUND
import com.rhmn.cakeorfake.tools.SharedPrefHandler.Constants.Companion.PREFERENCE_NAME
import com.rhmn.cakeorfake.ui.activity.game.model.GameData


class SharedPrefHandler(context: Context) {
    private var sharedPreference: SharedPreferences
    private var editor: SharedPreferences.Editor

    class Constants{
        companion object{
            const val PREFERENCE_NAME = "mSharedPrefHandler"
            const val FIRST_TIME_NAME = "first"
            const val GAME_NAME = "game"
            const val GAME_LEVEL_NAME = "level"
            const val GAME_COIN_NAME = "coin"
            const val GAME_EFFECT_SOUND = "effect"
            const val GAME_BACK_SOUND = "backSound"
            const val GAME_VOICE_SOUND = "voice"

        }
    }

    init {
        sharedPreference =  context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE)
        editor = sharedPreference.edit()
    }

    fun getGameList(): GameData{
        val game = sharedPreference.getString(GAME_NAME,"null")
        return Gson().fromJson(game, GameData::class.java)
    }

    fun setGameList(gameData: GameData){
        editor.putString(GAME_NAME,Gson().toJson(gameData))
        editor.commit()
    }

    fun isFirstTime():Boolean{
        return sharedPreference.getBoolean(FIRST_TIME_NAME, true)
    }

    fun itsFirstTime(){
        editor.putBoolean(FIRST_TIME_NAME,false)
        editor.commit()
    }

    fun setLevel(level: Int){
        editor.putInt(GAME_LEVEL_NAME,level)
        editor.commit()
    }

    fun getLevel(): Int {
        return sharedPreference.getInt(GAME_LEVEL_NAME, 1)

    }

    fun setCoin(coin: Int){
        editor.putInt(GAME_COIN_NAME,coin)
        editor.commit()
    }

    fun getCoin(): Int {
        return sharedPreference.getInt(GAME_COIN_NAME, 0)

    }

    fun setSoundEffect(status: Boolean){
        editor.putBoolean(GAME_EFFECT_SOUND,status)
        editor.commit()
    }

    fun getSoundEffect(): Boolean {
        return sharedPreference.getBoolean(GAME_EFFECT_SOUND, true)

    }

    fun setSoundBackground(status: Boolean){
        editor.putBoolean(GAME_BACK_SOUND,status)
        editor.commit()
    }

    fun getSoundBackground(): Boolean {
        return sharedPreference.getBoolean(GAME_BACK_SOUND, true)

    }

    fun setSoundVoice(status: Boolean){
        editor.putBoolean(GAME_VOICE_SOUND,status)
        editor.commit()
    }

    fun getSoundVoice(): Boolean {
        return sharedPreference.getBoolean(GAME_VOICE_SOUND, true)

    }
}