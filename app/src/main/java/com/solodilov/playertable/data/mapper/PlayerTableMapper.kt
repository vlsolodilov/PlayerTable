package com.solodilov.playertable.data.mapper

import com.solodilov.playertable.data.datasource.database.entity.PlayerDb
import com.solodilov.playertable.domain.entity.Player
import javax.inject.Inject

class PlayerTableMapper @Inject constructor() {

    fun mapPlayerDbToPlayer(player: PlayerDb): Player =
        Player(
            id = player.id,
            name = player.name,
        )

    fun mapPlayerToPlayerDb(player: Player): PlayerDb =
        PlayerDb(
            id = player.id,
            name = player.name,
        )

}