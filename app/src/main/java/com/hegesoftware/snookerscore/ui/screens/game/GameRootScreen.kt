package com.hegesoftware.snookerscore.ui.screens.game

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.hegesoftware.snookerscore.domain.models.Ball
import com.hegesoftware.snookerscore.domain.models.toBalls
import com.hegesoftware.snookerscore.domain.models.toOrdinalString
import com.hegesoftware.snookerscore.ui.components.*
import com.hegesoftware.snookerscore.ui.screens.GameRootTransitions
import com.ramcosta.composedestinations.annotation.Destination
import java.lang.Integer.max
import kotlin.math.abs

data class GameScreenNavArgs(
    val player1Name: String,
    val player2Name: String
)

@Destination(navArgsDelegate = GameScreenNavArgs::class, style = GameRootTransitions::class)
@Composable
fun GameRootScreen(
    viewModel: GameRootViewModel = hiltViewModel(),
) {
    val breakInfo by viewModel.breakInfo.collectAsState()
    val scoreInfo by viewModel.scoreInfo.collectAsState()
    val scoreDifference = remember(scoreInfo) {
        abs(scoreInfo.player1Score - scoreInfo.player2Score)
    }
    val legalBalls by viewModel.legalBalls.collectAsState()
    val foulInfo by viewModel.foulInfo.collectAsState()

    val settings by viewModel.settings.collectAsState()

    BackHandler {
        viewModel.onEvent(GameUiEvent.BackPressed)
    }

    LaunchedEffect(Unit) {
        viewModel.loadSettings()
    }

    if (viewModel.shouldShowAlertDialog) {
        val alertDialog = viewModel.getAlertDialog()
        CustomAlertDialog(
            titleText = alertDialog.titleText,
            yesText = alertDialog.yesText,
            noText = alertDialog.noText,
            bodyText = alertDialog.bodyText,
            onYes = alertDialog.onYes,
            onNo = alertDialog.onNo
        )
    }

    val swipeModifier = Modifier.onDrag(
        onDownSwipe = { viewModel.onEvent(GameUiEvent.DownSwiped) },
        onUpSwipe = { viewModel.onEvent(GameUiEvent.UpSwiped) },
        settings = settings,
    )

    Column(modifier =  swipeModifier) {
        ScoreDisplay(
            player1Name = if (!viewModel.reversePlayers) viewModel.player1Name else viewModel.player2Name,
            player2Name = if (!viewModel.reversePlayers) viewModel.player2Name else viewModel.player1Name,
            player1Score = scoreInfo.player1Score,
            player2Score = scoreInfo.player2Score,
            playerInTurn = breakInfo.playerInTurn
        )
        SmallSpacer()
        CurrentBreak(
            balls = breakInfo.breakPoints.toBalls(),
            topText = "${breakInfo.breakNumber.toOrdinalString()} break, ${breakInfo.breakPoints.sum()} points",
            bottomText = "${scoreInfo.pointsOnTable} on, difference of $scoreDifference",
            isFreeBall = legalBalls.isFreeBall,
            firstWasFreeBall = breakInfo.firstWasFreeBall
        )
        TinySpacer()

        if (viewModel.shouldShowFoulScreen) {
            val onKeyPress = defaultFoulButtonActions(viewModel)
            val onSwipe = defaultFoulSwipeActions(viewModel)

            FoulScreen(
                onKeyPress = onKeyPress,
                onSwipe = onSwipe,
                chosenFoul = foulInfo,
                lowestFoulPossible = max(legalBalls.lowestValue(), 4),
                redsRemaining = scoreInfo.redsRemaining,
                settings = settings
            )
        } else {
            val onKeyPress = defaultPointButtonActions(viewModel)
            val onSwipe = defaultPointSwipeActions(viewModel)

            PointScreen(
                onKeyPress = onKeyPress,
                onSwipe = onSwipe,
                legalBalls = legalBalls,
                redsRemaining = scoreInfo.redsRemaining,
                settings = settings
            )
        }
    }
}

private fun defaultPointButtonActions(viewModel: GameRootViewModel) = OnKeyPress.Point(
    red = { viewModel.onEvent(GameUiEvent.BallPressed(Ball.Red)) },
    yellow = { viewModel.onEvent(GameUiEvent.BallPressed(Ball.Yellow)) },
    green = { viewModel.onEvent(GameUiEvent.BallPressed(Ball.Green)) },
    brown = { viewModel.onEvent(GameUiEvent.BallPressed(Ball.Brown)) },
    blue = { viewModel.onEvent(GameUiEvent.BallPressed(Ball.Blue)) },
    pink = { viewModel.onEvent(GameUiEvent.BallPressed(Ball.Pink)) },
    black = { viewModel.onEvent(GameUiEvent.BallPressed(Ball.Black)) },
    undo = { viewModel.onEvent(GameUiEvent.UndoPressed) },
    menu = { viewModel.onEvent(GameUiEvent.MenuPressed) },
    foul = { viewModel.onEvent(GameUiEvent.FoulPressed) },
    endBreak = { viewModel.onEvent(GameUiEvent.EndBreakPressed) }
)

private fun defaultPointSwipeActions(viewModel: GameRootViewModel) = OnSwipe(
    up = { viewModel.onEvent(GameUiEvent.FoulPressed) },
    down = { viewModel.onEvent(GameUiEvent.EndBreakPressed) }
)

private fun defaultFoulButtonActions(viewModel: GameRootViewModel) = OnKeyPress.Foul(
    four = { viewModel.onEvent(GameUiEvent.PenaltyPressed(4)) },
    five = { viewModel.onEvent(GameUiEvent.PenaltyPressed(5)) },
    six = { viewModel.onEvent(GameUiEvent.PenaltyPressed(6)) },
    seven = { viewModel.onEvent(GameUiEvent.PenaltyPressed(7)) },
    minus = { viewModel.onEvent(GameUiEvent.DecreaseRedsLostPressed) },
    plus = { viewModel.onEvent(GameUiEvent.IncreaseRedsLostpressed) },
    freeBall = { viewModel.onEvent(GameUiEvent.FreeBallPressed) },
    confirm = { viewModel.onEvent(GameUiEvent.ConfirmFoulPressed) },
    undo = { viewModel.onEvent(GameUiEvent.UndoPressed) },
    menu = { viewModel.onEvent(GameUiEvent.MenuPressed) }
)

private fun defaultFoulSwipeActions(viewModel: GameRootViewModel) = OnSwipe(
    up = { viewModel.onEvent(GameUiEvent.UndoPressed) },
    down = { viewModel.onEvent(GameUiEvent.ConfirmFoulPressed) }
)

sealed class OnKeyPress {
    data class Point(
        val red: () -> Unit,
        val yellow: () -> Unit,
        val green: () -> Unit,
        val brown: () -> Unit,
        val blue: () -> Unit,
        val pink: () -> Unit,
        val black: () -> Unit,
        val foul: () -> Unit,
        val endBreak: () -> Unit,
        val undo: () -> Unit,
        val menu: () -> Unit
    ) : OnKeyPress()

    data class Foul(
        val four: () -> Unit,
        val five: () -> Unit,
        val six: () -> Unit,
        val seven: () -> Unit,
        val freeBall: () -> Unit,
        val minus: () -> Unit,
        val plus: () -> Unit,
        val confirm: () -> Unit,
        val undo: () -> Unit,
        val menu: () -> Unit
    ) : OnKeyPress()
}

data class OnSwipe(
    val up: () -> Unit,
    val down: () -> Unit
)
