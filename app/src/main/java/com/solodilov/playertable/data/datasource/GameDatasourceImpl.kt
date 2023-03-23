package com.solodilov.playertable.data.datasource

import com.solodilov.playertable.data.datasource.database.PlayerTableDao
import com.solodilov.playertable.data.mapper.PlayerTableMapper
import com.solodilov.playertable.domain.entity.Player
import javax.inject.Inject

class GameDatasourceImpl @Inject constructor(
    private val dao: PlayerTableDao,
    private val mapper: PlayerTableMapper,
) : GameDatasource {

    override suspend fun getPlayerList(): List<Player> =
        dao.getPlayerList().map (mapper::mapPlayerDbToPlayer)

    override suspend fun insertPlayers(players: List<Player>) {
        dao.insertPlayers(players.map (mapper::mapPlayerToPlayerDb))
    }

    override suspend fun insertGame(playerId: Int, opponentId: Int, score: Int) {
        dao.insertGame(playerId, opponentId, score)
    }

    override suspend fun getPlayerScore(playerId: Int): Map<Int, Int> =
        dao.getPlayerScore(playerId)

    override suspend fun getSumScore(playerId: Int): Int =
        dao.getSumScore(playerId)


    override suspend fun getGameCount(): Int =
        dao.getGameCount()

    override suspend fun getLeaderboard(): List<Int> =
        dao.getLeaderboard()

    override suspend fun getPlayerGameCount(playerId: Int): Int =
        dao.getPlayerGameCount(playerId)

    override suspend fun clearGame(playerId: Int, opponentId: Int) =
        dao.clearGame(playerId, opponentId)
}