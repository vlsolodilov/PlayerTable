package com.solodilov.playertable.presentation.model

data class PlayerUi(
    val id: Int,
    val name: String,
    val scores: List<Int?>,
    val sumScore: Int?,
    val rating: Int?,
)
