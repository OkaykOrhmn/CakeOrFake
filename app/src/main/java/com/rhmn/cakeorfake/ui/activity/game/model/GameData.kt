package com.rhmn.cakeorfake.ui.activity.game.model

import com.google.gson.annotations.SerializedName

data class GameData(
    @SerializedName("data")
    var data: Data
) {
    data class Data(
        @SerializedName("count")
        var count: Int = 0,
        @SerializedName("games")
        var levels: ArrayList<GameLevel>
    )
}
