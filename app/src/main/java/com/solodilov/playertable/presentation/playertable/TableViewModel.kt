package com.solodilov.playertable.presentation.playertable

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.solodilov.playertable.domain.usecase.*
import com.solodilov.playertable.presentation.common.UiState
import com.solodilov.playertable.presentation.model.PlayerUi
import com.solodilov.playertable.presentation.common.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TableViewModel @Inject constructor(
    private val getPlayerListUseCase: GetPlayerListUseCase,
    private val saveGameUseCase: SaveGameUseCase,
    private val getPlayerScoreUseCase: GetPlayerScoreUseCase,
    private val getPlayerScoreSumUseCase: GetPlayerScoreSumUseCase,
    private val getLeaderboardUseCase: GetLeaderboardUseCase,
    private val deleteGameUseCase: DeleteGameUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<PlayerUi>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<PlayerUi>>> = _uiState

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {

            flow {
                emit(Result.Loading)
                try {
                    emit(Result.Success(getPlayerUiList()))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }.collect { result ->
                _uiState.value = when (result) {
                    is Result.Loading -> UiState.Loading
                    is Result.Success -> UiState.Success(result.data)
                    is Result.Error -> UiState.Error(result.exception)
                }
            }
        }
    }

    private suspend fun getPlayerUiList(): List<PlayerUi> = withContext(Dispatchers.IO) {
        val players = getPlayerListUseCase()
        players.map { player ->
            PlayerUi(
                id = player.id,
                name = player.name,
                scores = getScoreList(players.size, getPlayerScoreUseCase(player.id)),
                sumScore = getPlayerScoreSumUseCase(player.id),
                rating = getRating(player.id, getLeaderboardUseCase())
            )
        }.toMutableList().also { list ->
            list.add(0, PlayerUi(0, "", List(players.size) { (it + 1) }, null, null))
        }
    }

    private fun getScoreList(listSize: Int, scoreMap: Map<Int, Int>): List<Int?> {
        val list: MutableList<Int?> = MutableList(listSize) { null }
        scoreMap.forEach { list[it.key - 1] = it.value }
        return list
    }

    private fun getRating(playerId: Int, leaderboard: List<Int>): Int? {
        return if (leaderboard.isNotEmpty()) leaderboard.indexOf(playerId) + 1 else null
    }

    fun changeGame(playerId: Int, opponentId: Int, score: Int?) {
        viewModelScope.launch {
            if (score != null) {
                saveGameUseCase(playerId, opponentId, score)
            } else {
                deleteGameUseCase(playerId, opponentId)
            }
        }
    }

}