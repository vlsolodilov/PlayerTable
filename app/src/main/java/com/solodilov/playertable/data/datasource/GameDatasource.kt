package com.solodilov.playertable.data.datasource

import com.solodilov.playertable.domain.entity.Player

interface GameDatasource {

    suspend fun getPlayerList(): List<Player>

    suspend fun insertPlayers(players: List<Player>)

    suspend fun insertGame(playerId: Int, opponentId: Int, score: Int)

    suspend fun getPlayerScore(playerId: Int): Map<Int, Int>

    suspend fun getSumScore(playerId: Int): Int?

    suspend fun getGameCount(): Int

    suspend fun getLeaderboard(): List<Int>

    suspend fun getPlayerGameCount(playerId: Int): Int?

    suspend fun clearGame(playerId: Int, opponentId: Int)
}