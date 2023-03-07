package com.hegesoft.snookerscore.ui.screens.new_game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.hegesoft.snookerscore.data.SharedPref
import com.hegesoft.snookerscore.ui.screens.Navigator
import com.hegesoft.snookerscore.ui.screens.destinations.GameRootScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.min

@HiltViewModel
class NewGameViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val navigator: Navigator,
    private val sharedPref: SharedPref
) : ViewModel() {

    var gameState by mutableStateOf(GameState())
        private set

    var shouldShowAlertDialog by mutableStateOf(false)
        private set

    private val player1NavArgument: String
        get() = gameState.player1Name.trim().ifEmpty { "Player 1" }
    private val player2NavArgument: String
        get() = gameState.player2Name.trim().ifEmpty { "Player 2" }

    fun onEvent(event: NewGameUiEvent) {
        when (event) {
            is NewGameUiEvent.Player1Changed -> {
                gameState = gameState.copy(player1Name = limitedLengthName(event.value))
            }
            is NewGameUiEvent.Player2Changed -> {
                gameState = gameState.copy(player2Name = limitedLengthName(event.value))
            }
            NewGameUiEvent.GoToFramePressed -> {
                if (!gameState.gameExists) {
                    sharedPref.setGameToExist()
                    sharedPref.setPlayerNames(
                        player1Name = player1NavArgument,
                        player2Name = player2NavArgument
                    )
                }
                navigator.navigate(
                    GameRootScreenDestination(
                        player1NavArgument,
                        player2NavArgument
                    )
                )
            }
            NewGameUiEvent.DeleteGamePressed -> shouldShowAlertDialog = true
            NewGameUiEvent.CancelDeleteGamePressed -> shouldShowAlertDialog = false
            NewGameUiEvent.ConfirmDeleteGamePressed -> {
                shouldShowAlertDialog = false
                sharedPref.resetGame()
                loadGameState()
            }
        }
    }

    private fun limitedLengthName(name: String): String {
        val allowedLength = 20

        return name.substring(0, min(name.length, allowedLength))
    }

    fun loadGameState() {
        val (player1Name, player2Name) = sharedPref.getPlayerNames()
        val (player1GameScore, player2GameScore) = sharedPref.getGameScore()
        val gameExists = sharedPref.getGameExistence()
        val frameExists = sharedPref.getFrameExistence()

        gameState = GameState(
            player1Name = player1Name,
            player2Name = player2Name,
            player1GameScore = player1GameScore,
            player2GameScore = player2GameScore,
            gameExists = gameExists,
            frameExists = frameExists
        )
    }
}