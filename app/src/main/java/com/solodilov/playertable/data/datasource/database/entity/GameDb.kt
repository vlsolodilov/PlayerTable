package com.solodilov.playertable.data.datasource.database.entity

import androidx.room.Entity

@Entity(
    tableName = "game",
    primaryKeys = ["playerId", "opponentId"]
)
data class GameDb(
    val playerId: Int,
    val opponentId: Int,
    val score: Int,
)
