package com.hegesoftware.snookerscore.ui.screens.game

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.hegesoftware.snookerscore.domain.models.Ball
import com.hegesoftware.snookerscore.domain.models.Foul
import com.hegesoftware.snookerscore.domain.models.Settings
import com.hegesoftware.snookerscore.ui.components.*

@Composable
fun FoulScreen(
    onKeyPress: OnKeyPress.Foul,
    onSwipe: OnSwipe,
    chosenFoul: Foul,
    lowestFoulPossible: Int,
    redsRemaining: Int,
    settings: Settings,
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
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        val modifier = Modifier.weight(1f, false)

        Column(modifier = modifier) {
            Row(modifier = modifier) {
                CustomIconButton(
                    modifier = modifier.create(onKeyPress.menu),
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Settings",
                    onPressed = onKeyPress.menu
                )
                TinyHorizontalSpacer()
                CharButton(
                    modifier = if (lowestFoulPossible <= 4) modifier.create(onKeyPress.four) else modifier,
                    char = '4',
                    isSelected = chosenFoul.penaltyPoints == 4,
                    isVisible = lowestFoulPossible <= 4,
                    onPressed = onKeyPress.four
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
            Row(modifier = modifier) {
                CharButton(
                    modifier = if (lowestFoulPossible <= 5) modifier.create(onKeyPress.five) else modifier,
                    char = '5',
                    isSelected = chosenFoul.penaltyPoints == 5,
                    isVisible = lowestFoulPossible <= 5,
                    onPressed = onKeyPress.five
                )
                TinyHorizontalSpacer()
                CharButton(
                    modifier = if (lowestFoulPossible <= 6) modifier.create(onKeyPress.six) else modifier,
                    char = '6',
                    isSelected = chosenFoul.penaltyPoints == 6,
                    isVisible = lowestFoulPossible <= 6,
                    onPressed = onKeyPress.six
                )
                TinyHorizontalSpacer()
                CharButton(
                    modifier = if (lowestFoulPossible <= 7) modifier.create(onKeyPress.seven) else modifier,
                    char = '7',
                    isSelected = chosenFoul.penaltyPoints == 7,
                    isVisible = lowestFoulPossible <= 7,
                    onPressed = onKeyPress.seven
                )
            }
            TinyVerticalSpacer()
            Row(modifier = modifier) {
                CharButton(
                    modifier = modifier.create(onKeyPress.minus),
                    char = '-',
                    isSelected = false,
                    isVisible = chosenFoul.redsLost > 0,
                    onPressed = onKeyPress.minus
                )
                TinyHorizontalSpacer()
                ContentButton(
                    modifier = modifier.create(onKeyPress.plus),
                    enabled = chosenFoul.redsLost < redsRemaining,
                    onPressed = onKeyPress.plus
                ) {
                    if (redsRemaining > 0) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            val textStyle = MaterialTheme.typography.displaySmall
                            Text(
                                text = "Lost",
                                style = textStyle
                            )
                            Ball(
                                ball = Ball.Red,
                                modifier = modifier
                            )
                            Text(
                                text = chosenFoul.redsLost.toString(),
                                style = MaterialTheme.typography.displaySmall
                            )
                        }
                    }
                }
                TinyHorizontalSpacer()
                ContentButton(
                    modifier = modifier.create(onKeyPress.freeBall),
                    enabled = lowestFoulPossible < 7,
                    isSelected = chosenFoul.isFreeBall,
                    onPressed = onKeyPress.freeBall
                ) {
                    if (lowestFoulPossible < 7) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            val textStyle = MaterialTheme.typography.displaySmall
                            Text(
                                text = "Free\nBall",
                                style = textStyle,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
        if (!settings.swipingEnabled || !settings.hideButtonsEnabled) {
            Column {
                SmallSpacer()
                WideButton(
                    text = "Confirm foul",
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    onPressed = onKeyPress.confirm
                )
            }
        }
    }

}