package com.solodilov.playertable.domain.usecase

import com.solodilov.playertable.domain.entity.Player
import com.solodilov.playertable.domain.repository.GameRepository
import javax.inject.Inject

class GetPlayerListUseCase @Inject constructor(private val gameRepository: GameRepository) {

    suspend operator fun invoke(): List<Player> = gameRepository.getPlayerList()
}