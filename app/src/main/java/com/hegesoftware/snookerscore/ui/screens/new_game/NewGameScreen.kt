package com.hegesoftware.snookerscore.ui.screens.new_game

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hegesoftware.snookerscore.ui.components.*
import com.hegesoftware.snookerscore.ui.screens.NewGameTransitions
import com.hegesoftware.snookerscore.ui.theme.Typography
import com.hegesoftware.snookerscore.ui.theme.padding
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@Destination(style = NewGameTransitions::class)
@Composable
fun NewGameScreen(
    viewModel: NewGameViewModel = hiltViewModel(),
) {
    val gameState = viewModel.gameState

    LaunchedEffect(Unit) {
        viewModel.loadGameState()
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

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text("SnookerScore", style = Typography.displayMedium)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { viewModel.onEvent(NewGameUiEvent.GoToLicensesPressed) }) {
                    Icon(imageVector = Icons.Outlined.Info, "Information")
                }
            }
        }

        if (!gameState.gameExists) {
            Column {
                CustomTextField(
                    label = "Player 1",
                    value = gameState.player1Name,
                    keyboardAction = KeyboardAction.GoNext,
                    onValueChanged = { viewModel.onEvent(NewGameUiEvent.Player1Changed(it)) }
                )
                MediumSpacer()
                CustomTextField(
                    label = "Player 2",
                    value = gameState.player2Name,
                    keyboardAction = KeyboardAction.Finish,
                    onValueChanged = { viewModel.onEvent(NewGameUiEvent.Player2Changed(it)) }
                )
            }
        } else {
            GameScore(
                player1Name = gameState.player1Name,
                player2Name = gameState.player2Name,
                player1GameScore = gameState.player1GameScore,
                player2GameScore = gameState.player2GameScore
            )
        }


        Column {
            if (gameState.gameExists) {
                WideButton(
                    text = "Finish game",
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    viewModel.onEvent(NewGameUiEvent.DeleteGamePressed)
                }
            }
            SmallSpacer()

            val buttonText = remember(gameState.frameExists) {
                if (gameState.frameExists) "Continue frame" else "New frame"
            }
            WideButton(
                text = buttonText,
                color = MaterialTheme.colorScheme.tertiaryContainer
            ) {
                viewModel.onEvent(NewGameUiEvent.GoToFramePressed)
            }
        }
    }
}

@Composable
private fun GameScore(
    player1Name: String,
    player2Name: String,
    player1GameScore: Int,
    player2GameScore: Int,
) {
    Surface(
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primaryContainer),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(horizontal = MaterialTheme.padding.small)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
        ) {
            val textStyle = MaterialTheme.typography.displayMedium
            val modifier = Modifier.weight(1f)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = player1Name,
                    style = textStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = modifier
                )
                Text(
                    text = player1GameScore.toString(),
                    style = textStyle
                )
            }
            SmallSpacer()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = player2Name,
                    style = textStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = modifier
                )
                Text(
                    text = player2GameScore.toString(),
                    style = textStyle
                )
            }
        }
    }
}