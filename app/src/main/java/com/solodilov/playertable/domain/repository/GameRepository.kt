package com.solodilov.playertable.domain.repository

import com.solodilov.playertable.domain.entity.Player

interface GameRepository {

    suspend fun getPlayerList(): List<Player>
    suspend fun getLeaderboard(): List<Int>
    suspend fun getPlayerScoreSum(playerId: Int): Int?
    suspend fun insertPlayers(players: List<Player>)
    suspend fun insertGame(playerId: Int, opponentId: Int, score: Int)
    suspend fun getPlayerScore(playerId: Int): Map<Int, Int>
    suspend fun deleteGame(playerId: Int, opponentId: Int)
}