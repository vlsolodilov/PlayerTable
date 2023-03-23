package com.solodilov.playertable.data.datasource.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player")
data class PlayerDb(
    @PrimaryKey
    val id: Int,
    val name: String,
)
