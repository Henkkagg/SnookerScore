package com.hegesoftware.snookerscore.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.hegesoftware.snookerscore.ui.components.CheckboxRow
import com.hegesoftware.snookerscore.ui.components.SmallSpacer
import com.hegesoftware.snookerscore.ui.components.WideButton
import com.hegesoftware.snookerscore.ui.screens.SettingsTransitions
import com.hegesoftware.snookerscore.ui.theme.padding
import com.ramcosta.composedestinations.annotation.Destination

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
            if (viewModel.settings.swipingEnabled) {
                SmallSpacer()
                CheckboxRow(
                    settingName = "Hide bottom buttons",
                    settingExplanation = "If you have drag gestures enabled, you can hide bottom buttons to free up screen space",
                    isChecked = viewModel.settings.hideButtonsEnabled,
                    onCheckedChange = { viewModel.onEvent(SettingsUiEvent.HideButtonsPressed) }
                )
            }
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