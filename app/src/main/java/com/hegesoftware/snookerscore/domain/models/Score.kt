package com.hegesoftware.snookerscore.domain.models

data class Score(
    val player1Score: Int = 0,
    val player2Score: Int = 0,
    val pointsOnTable: Int = 147,
    val redsRemaining: Int = 15
)
