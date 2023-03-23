package com.solodilov.playertable.domain.usecase

import com.solodilov.playertable.domain.repository.GameRepository
import javax.inject.Inject

class SaveGameUseCase @Inject constructor(private val gameRepository: GameRepository) {

    suspend operator fun invoke(playerId: Int, opponentId: Int, score: Int) =
        gameRepository.insertGame(playerId, opponentId, score)
}