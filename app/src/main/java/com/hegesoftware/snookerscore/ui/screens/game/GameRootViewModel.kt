package com.hegesoftware.snookerscore.ui.screens.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegesoftware.snookerscore.data.SharedPref
import com.hegesoftware.snookerscore.data.sum
import com.hegesoftware.snookerscore.domain.BreakUsecases
import com.hegesoftware.snookerscore.domain.models.*
import com.hegesoftware.snookerscore.ui.screens.Navigator
import com.hegesoftware.snookerscore.ui.screens.destinations.SettingsScreenDestination
import com.hegesoftware.snookerscore.ui.screens.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Integer.max
import javax.inject.Inject

@HiltViewModel
class GameRootViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val usecases: BreakUsecases,
    private val navigator: Navigator,
    private val sharedPref: SharedPref
) : ViewModel() {

    val player1Name = savedStateHandle.navArgs<GameScreenNavArgs>().player1Name
    val player2Name = savedStateHandle.navArgs<GameScreenNavArgs>().player2Name

    private val _scoreInfo = MutableStateFlow(Score())
    val scoreInfo = _scoreInfo.asStateFlow()

    //Starting player changes every frame
    val reversePlayers: Boolean = sharedPref.getGameScore().sum() % 2 != 0

    private val _breakInfo = MutableStateFlow(BreakUi())
    val breakInfo = _breakInfo.asStateFlow()

    private val _legalBalls = MutableStateFlow(LegalBalls())
    val legalBalls = _legalBalls.asStateFlow()

    var shouldShowFoulScreen by mutableStateOf(false)
        private set

    private val _foulInfo = MutableStateFlow(Foul())
    val foulInfo = _foulInfo.asStateFlow()

    var shouldShowAlertDialog by mutableStateOf(false)
        private set

    private val _settings = MutableStateFlow(sharedPref.getSettings())
    val settings = _settings.asStateFlow()

    init {

        viewModelScope.launch {
            if (!sharedPref.getFrameExistence()) {
                usecases.newGame()
                sharedPref.setFrameExistance(frameExists = true)
            }

            usecases.getBreakStream().collect { receivedBreakUi ->
                _breakInfo.value = receivedBreakUi
            }
        }
        viewModelScope.launch {
            usecases.getLegalBallsStream().collect { legalBalls ->
                _legalBalls.value = legalBalls

                if (legalBalls.lowestValue() == 404) shouldShowAlertDialog = true

                //To prevent PointScreen from recomposing with old information. Could not fix cleanly
                delay(5)
                shouldShowFoulScreen = false
            }
        }

        viewModelScope.launch {
            usecases.getScoreStream().collect { scoreInformation ->
                _scoreInfo.value = scoreInformation
            }
        }
    }

    fun onEvent(event: GameUiEvent) {
        when (event) {
            GameUiEvent.MenuPressed -> navigator.navigate(SettingsScreenDestination)
            GameUiEvent.FoulPressed -> {
                initializeFoul()
                shouldShowFoulScreen = true
            }
            GameUiEvent.UndoPressed -> {
                if (shouldShowFoulScreen) {
                    shouldShowFoulScreen = false
                } else viewModelScope.launch { usecases.undo() }
            }
            GameUiEvent.EndBreakPressed -> viewModelScope.launch { usecases.newBreak() }
            is GameUiEvent.BallPressed -> viewModelScope.launch {
                usecases.addPoint(event.ball)
            }
            GameUiEvent.DecreaseRedsLostPressed -> changeRedsLost(-1)
            GameUiEvent.IncreaseRedsLostpressed -> changeRedsLost(1)
            is GameUiEvent.PenaltyPressed -> {
                _foulInfo.update { it.copy(penaltyPoints = event.penaltyPoints) }
            }
            GameUiEvent.FreeBallPressed -> _foulInfo.update { it.copy(isFreeBall = !it.isFreeBall) }
            is GameUiEvent.ConfirmFoulPressed -> viewModelScope.launch { usecases.addFoul(foulInfo.value) }
            GameUiEvent.DownSwiped -> {
                if (shouldShowFoulScreen) {
                    viewModelScope.launch { usecases.addFoul(foulInfo.value) }
                } else {
                    viewModelScope.launch { usecases.newBreak() }
                }
            }
            GameUiEvent.UpSwiped -> {
                initializeFoul()
                shouldShowFoulScreen = !shouldShowFoulScreen
            }
            GameUiEvent.BackPressed -> shouldShowAlertDialog = !shouldShowAlertDialog
            GameUiEvent.EndFramePressed -> {
                shouldShowAlertDialog = false
                val playerWhoWon = when {
                    scoreInfo.value.player1Score > scoreInfo.value.player2Score -> if (!reversePlayers) 1 else 2
                    scoreInfo.value.player2Score > scoreInfo.value.player1Score -> if (!reversePlayers) 2 else 1
                    else -> null
                }
                if (playerWhoWon != null) sharedPref.addFrameWin(playerWhoWon)
                sharedPref.setFrameExistance(frameExists = false)
                navigator.popBackStack()
            }
        }
    }

    fun loadSettings() {
        _settings.value = sharedPref.getSettings()
    }

    fun getAlertDialog(): AlertDialog {
        val frameIsTied = scoreInfo.value.player1Score == scoreInfo.value.player2Score
        val player1Won = scoreInfo.value.player1Score > scoreInfo.value.player2Score
        val winner = when {
            player1Won && !reversePlayers -> player1Name
            player1Won && reversePlayers -> player2Name
            !player1Won && !reversePlayers -> player2Name
            else -> player1Name
        }
        val titleText = if (frameIsTied) "Discard frame?" else "End frame?"
        val bodyText = if (frameIsTied) {
            "The score of the frame is tied. If you end the frame now, the result won't be recorded"
        } else "If you end the frame now, $winner will be recorded as the winner"
        val yesText = if (frameIsTied) "Discard" else "End"

        return AlertDialog(
            titleText = titleText,
            bodyText = bodyText,
            yesText = yesText,
            onYes = { onEvent(GameUiEvent.EndFramePressed) },
            onNo = { onEvent(GameUiEvent.BackPressed) }
        )
    }

    //Prevents user from button smashing and getting unintended result before buttons are disabled
    private fun changeRedsLost(changeAmount: Int) {
        val proposedRedsLot = foulInfo.value.redsLost + changeAmount
        if (proposedRedsLot > scoreInfo.value.redsRemaining || proposedRedsLot < 0) return

        _foulInfo.update { it.copy(redsLost = proposedRedsLot) }
    }

    private fun initializeFoul() {
        _foulInfo.value = Foul(
            penaltyPoints = max(legalBalls.value.lowestValue(), 4)
        )
    }
}