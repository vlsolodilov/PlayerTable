package com.solodilov.playertable.domain.usecase

import com.solodilov.playertable.domain.repository.GameRepository
import javax.inject.Inject

class GetLeaderboardUseCase @Inject constructor(private val gameRepository: GameRepository) {

    suspend operator fun invoke(): List<Int> =
        gameRepository.getLeaderboard()
}