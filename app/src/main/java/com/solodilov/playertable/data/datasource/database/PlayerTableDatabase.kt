package com.solodilov.playertable.data.datasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.solodilov.playertable.data.datasource.database.entity.GameDb
import com.solodilov.playertable.data.datasource.database.entity.PlayerDb

@Database(
    entities = [
        PlayerDb::class,
        GameDb::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PlayerTableDatabase : RoomDatabase() {

    abstract fun playerTableDao(): PlayerTableDao

    companion object {

        @Volatile
        private var INSTANCE: PlayerTableDatabase? = null

        fun getInstance(context: Context): PlayerTableDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PlayerTableDatabase::class.java, "PlayerTable.db"
            ).build()
    }
}
