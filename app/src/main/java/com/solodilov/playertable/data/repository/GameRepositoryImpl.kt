package com.solodilov.playertable.data.repository

import com.solodilov.playertable.data.datasource.GameDatasource
import com.solodilov.playertable.domain.entity.Player
import com.solodilov.playertable.domain.repository.GameRepository
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val datasource: GameDatasource,
) : GameRepository {

    override suspend fun getPlayerList(): List<Player> =
        datasource.getPlayerList().ifEmpty { players.also { insertPlayers(it) } }


    override suspend fun getLeaderboard(): List<Int> =
        if (datasource.getGameCount() == getPlayerList().size * (getPlayerList().size - 1))
            datasource.getLeaderboard()
        else
            emptyList()

    override suspend fun getPlayerScoreSum(playerId: Int): Int? =
        if (datasource.getPlayerGameCount(playerId) == getPlayerList().size - 1)
            datasource.getSumScore(playerId)
        else
            null

    override suspend fun insertPlayers(players: List<Player>) {
        datasource.insertPlayers(players)
    }

    override suspend fun insertGame(playerId: Int, opponentId: Int, score: Int) =
        datasource.insertGame(playerId, opponentId, score)

    override suspend fun getPlayerScore(playerId: Int): Map<Int, Int> =
        datasource.getPlayerScore(playerId)

    override suspend fun deleteGame(playerId: Int, opponentId: Int) {
        datasource.clearGame(playerId, opponentId)
    }

}

val players = listOf(
    Player(1, "Участник 1"),
    Player(2, "Участник 2"),
    Player(3, "Участник 3"),
    Player(4, "Участник 4"),
    Player(5, "Участник 5"),
    Player(6, "Участник 6"),
    Player(7, "Участник 7"),
)