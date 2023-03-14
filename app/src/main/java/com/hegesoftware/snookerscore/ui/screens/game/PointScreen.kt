package com.hegesoftware.snookerscore.ui.screens.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hegesoftware.snookerscore.domain.models.Ball
import com.hegesoftware.snookerscore.domain.models.LegalBalls
import com.hegesoftware.snookerscore.domain.models.Settings
import com.hegesoftware.snookerscore.ui.components.*

@Composable
fun PointScreen(
    onKeyPress: OnKeyPress.Point,
    onSwipe: OnSwipe,
    legalBalls: LegalBalls,
    redsRemaining: Int,
    settings: Settings
) {
    fun Modifier.create(action: () -> Unit): Modifier {

        return this.onDrag(
            onDownSwipe = onSwipe.down,
            onUpSwipe = onSwipe.up,
            onSloppyPress = action,
            settings = settings
        )
    }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxHeight()
    ) {
        val modifier = Modifier.weight(1f)

        Column {
            Row {
                CustomIconButton(
                    modifier = modifier.create(onKeyPress.menu),
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Settings",
                    onPressed = onKeyPress.menu
                )
                TinyHorizontalSpacer()
                BallButton(
                    modifier = if (legalBalls.red) modifier.create(onKeyPress.red) else modifier,
                    ball = Ball.Red,
                    ballCount = if (settings.showRedsRemaining) redsRemaining else null,
                    enabled = legalBalls.red,
                    onPressed = onKeyPress.red
                )
                TinyHorizontalSpacer()
                CustomIconButton(
                    modifier = modifier.create(onKeyPress.undo),
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Undo",
                    onPressed = onKeyPress.undo
                )
            }
            TinyVerticalSpacer()
            Row {
                BallButton(
                    modifier = if (legalBalls.yellow) modifier.create(onKeyPress.yellow) else modifier,
                    ball = Ball.Yellow,
                    enabled = legalBalls.yellow,
                    onPressed = onKeyPress.yellow
                )
                TinyHorizontalSpacer()
                BallButton(
                    modifier = if (legalBalls.green) modifier.create(onKeyPress.green) else modifier,
                    ball = Ball.Green,
                    enabled = legalBalls.green,
                    onPressed = onKeyPress.green
                )
                TinyHorizontalSpacer()
                BallButton(
                    modifier = if (legalBalls.brown) modifier.create(onKeyPress.brown) else modifier,
                    ball = Ball.Brown,
                    enabled = legalBalls.brown,
                    onPressed = onKeyPress.brown
                )
            }
            TinyVerticalSpacer()
            Row {
                BallButton(
                    modifier = if (legalBalls.blue) modifier.create(onKeyPress.blue) else modifier,
                    ball = Ball.Blue,
                    enabled = legalBalls.blue,
                    onPressed = onKeyPress.blue
                )
                TinyHorizontalSpacer()
                BallButton(
                    modifier = if (legalBalls.pink) modifier.create(onKeyPress.pink) else modifier,
                    ball = Ball.Pink,
                    enabled = legalBalls.pink,
                    onPressed = onKeyPress.pink
                )
                TinyHorizontalSpacer()
                BallButton(
                    modifier = if (legalBalls.black) modifier.create(onKeyPress.black) else modifier,
                    ball = Ball.Black,
                    enabled = legalBalls.black,
                    onPressed = onKeyPress.black
                )
            }
        }

        Column {
            WideButton(
                text = "Foul",
                color = MaterialTheme.colorScheme.primaryContainer,
                onPressed = onKeyPress.foul
            )
            SmallSpacer()
            WideButton(
                text = "End break",
                color = MaterialTheme.colorScheme.tertiaryContainer,
                onPressed = onKeyPress.endBreak
            )
        }
    }
}
