package com.hegesoftware.snookerscore.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hegesoftware.snookerscore.ui.theme.padding

@Composable
fun ScoreDisplay(
    player1Name: String,
    player2Name: String,
    player1Score: Int,
    player2Score: Int,
    playerInTurn: Int
) {
    Column {
        ScoreRow(name = player1Name, score = player1Score, isInTurn = playerInTurn == 1)
        ScoreRow(name = player2Name, score = player2Score, isInTurn = playerInTurn == 2)
    }
}

@Composable
private fun ScoreRow(
    name: String,
    score: Int,
    isInTurn: Boolean
) {
    val backgroundColor = if (isInTurn) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        Color.Transparent
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(horizontal = MaterialTheme.padding.small)
    ) {
        val textStyle = MaterialTheme.typography.displayMedium
        Text(
            text = name,
            style = textStyle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = score.toString(),
            style = textStyle,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}