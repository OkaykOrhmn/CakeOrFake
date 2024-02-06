package com.rhmn.cakeorfake.tools

import android.R.drawable
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import com.rhmn.cakeorfake.ui.activity.game.model.GameData
import com.rhmn.cakeorfake.ui.activity.game.model.Level
import com.rhmn.cakeorfake.ui.activity.home.HomeActivity
import java.lang.reflect.Field


class Utils {

    companion object {
        fun goToActivity(context: Context, targetActivity:  Class<*>?){
            val intent = Intent(context, targetActivity)
            context.startActivity(intent)
        }
        fun finishActivity(context: Context) {

            val activity = context as? Activity
            activity!!.finish()

        }

        fun finishActivityGoHome(context: Context) {

            val activity = context as? Activity
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
            activity!!.finishAffinity()

        }
        fun finishAllActivity(context: Context) {

            val activity = context as? Activity
            activity!!.finishAffinity()

        }

        fun createData(context: Context) : GameData{
            val fileInString: String =
                context.assets.open("game-data.json").bufferedReader().use { it.readText() }
            return Gson().fromJson(fileInString, GameData::class.java)
        }

        fun getLevels(context: Context):List<Level>{
            val sharedPrefHandler = SharedPrefHandler(context)
            val lvls : MutableList<Level> = mutableListOf()
            val positionUnlock = sharedPrefHandler.getLevel()
            sharedPrefHandler.getGameList().data.levels.forEach {
                val isLock = it.level >=  positionUnlock +1
                lvls += Level(it.level,isLock)
            }
            return lvls
        }

        @SuppressLint("DiscouragedApi")
        fun getImageDrawable(context: Context, name: String):Int{
            val resID: Int =
                context.resources.getIdentifier(name, "drawable", context.packageName)
           return resID

        }
    }
}