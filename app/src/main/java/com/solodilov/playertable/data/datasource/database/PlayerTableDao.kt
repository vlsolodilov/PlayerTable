package com.solodilov.playertable.data.datasource.database

import androidx.room.*
import com.solodilov.playertable.data.datasource.database.entity.GameDb
import com.solodilov.playertable.data.datasource.database.entity.PlayerDb

@Dao
interface PlayerTableDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayers(players: List<PlayerDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOnePlayerGame(game: GameDb)

    @Query("SELECT * FROM player")
    suspend fun getPlayerList(): List<PlayerDb>

    @MapInfo(keyColumn = "opponentId", valueColumn = "score")
    @Query("SELECT opponentId, score FROM game WHERE playerId =:playerId")
    fun getPlayerScore(playerId: Int): Map<Int, Int>

    @Query("SELECT SUM(score) FROM game WHERE playerId =:playerId")
    fun getSumScore(playerId: Int): Int

    @Query("SELECT COUNT(*) FROM game")
    suspend fun getGameCount(): Int

    @Query("SELECT playerId FROM game GROUP BY playerId ORDER BY SUM(score) DESC")
    suspend fun getLeaderboard(): List<Int>

    @Query("SELECT COUNT(*) FROM game WHERE playerId =:playerId")
    suspend fun getPlayerGameCount(playerId: Int): Int

    @Query("DELETE FROM game WHERE playerId =:playerId AND opponentId =:opponentId")
    suspend fun clearOnePlayerGame(playerId: Int, opponentId: Int)

    @Transaction
    suspend fun insertGame(playerId: Int, opponentId: Int, score: Int) {
        insertOnePlayerGame(GameDb(playerId, opponentId, score))
        insertOnePlayerGame(GameDb(opponentId, playerId, score))
    }

    @Transaction
    suspend fun clearGame(playerId: Int, opponentId: Int) {
        clearOnePlayerGame(playerId, opponentId)
        clearOnePlayerGame(opponentId, playerId)
    }

}