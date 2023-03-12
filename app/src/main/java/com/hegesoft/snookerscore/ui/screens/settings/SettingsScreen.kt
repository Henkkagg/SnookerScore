package com.hegesoft.snookerscore.ui.screens.settings

import androidx.compose.animation.*
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.hegesoft.snookerscore.ui.components.CheckboxRow
import com.hegesoft.snookerscore.ui.components.SmallSpacer
import com.hegesoft.snookerscore.ui.components.WideButton
import com.hegesoft.snookerscore.ui.theme.padding
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.spec.DestinationStyle

@OptIn(ExperimentalAnimationApi::class)
object SettingsTransitions : DestinationStyle.Animated {
    override fun AnimatedContentScope<NavBackStackEntry>.enterTransition(): EnterTransition? {
        return slideInVertically(
            animationSpec = TweenSpec(),
            initialOffsetY = { it }
        )
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popExitTransition(): ExitTransition? {
        return slideOutVertically(
            animationSpec = TweenSpec(),
            targetOffsetY = { it }
        )
    }
}

@Destination(style = SettingsTransitions::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = MaterialTheme.padding.small),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            CheckboxRow(
                settingName = "Drag gestures",
                settingExplanation = "Swipe up to switch between ball and foul selection. Swipe down to end break or confirm foul",
                isChecked = viewModel.settings.swipingEnabled,
                onCheckedChange = { viewModel.onEvent(SettingsUiEvent.DragPressed) }
            )
            SmallSpacer()
            CheckboxRow(
                settingName = "Easier pressing",
                settingExplanation = "Register button presses even if your finger moves after the beginning of the touch",
                isChecked = viewModel.settings.sloppyPressEnabled,
                onCheckedChange = { viewModel.onEvent(SettingsUiEvent.SloppyPressed) }
            )
            SmallSpacer()
            CheckboxRow(
                settingName = "Show red count",
                settingExplanation = "Display the amount of reds remaining",
                isChecked = viewModel.settings.showRedsRemaining,
                onCheckedChange = { viewModel.onEvent(SettingsUiEvent.ShowRedsPressed) }
            )
        }
        WideButton(
            text = "Return",
            color = MaterialTheme.colorScheme.tertiaryContainer,
            onPressed = { viewModel.onEvent(SettingsUiEvent.ReturnPressed) })
    }
}