package com.solodilov.playertable.domain.usecase

import com.solodilov.playertable.domain.repository.GameRepository
import javax.inject.Inject

class GetPlayerScoreSumUseCase @Inject constructor(private val gameRepository: GameRepository) {

    suspend operator fun invoke(playerId: Int): Int? =
        gameRepository.getPlayerScoreSum(playerId)
}