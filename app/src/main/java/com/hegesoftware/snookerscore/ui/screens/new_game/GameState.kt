package com.hegesoftware.snookerscore.ui.screens.new_game

data class GameState(
    val player1Name: String = "",
    val player2Name: String = "",
    val player1GameScore: Int = 0,
    val player2GameScore: Int = 0,
    val gameExists: Boolean = false,
    val frameExists: Boolean = false
)