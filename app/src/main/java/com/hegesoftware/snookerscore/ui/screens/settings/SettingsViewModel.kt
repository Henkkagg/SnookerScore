package com.hegesoftware.snookerscore.ui.screens.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hegesoftware.snookerscore.data.SharedPref
import com.hegesoftware.snookerscore.ui.screens.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val sharedPref: SharedPref,
    private val navigator: Navigator
) : ViewModel() {

    var settings by mutableStateOf(sharedPref.getSettings())
        private set

    fun onEvent(event: SettingsUiEvent) {
        when (event) {
            SettingsUiEvent.DragPressed -> {
                settings = settings.copy(swipingEnabled = !settings.swipingEnabled)
            }
            SettingsUiEvent.HideButtonsPressed -> {
                settings = settings.copy(hideButtonsEnabled = !settings.hideButtonsEnabled)
            }
            SettingsUiEvent.SloppyPressed -> {
                settings = settings.copy(sloppyPressEnabled = !settings.sloppyPressEnabled)
            }
            SettingsUiEvent.ShowRedsPressed -> {
                settings = settings.copy(showRedsRemaining = !settings.showRedsRemaining)
            }
            SettingsUiEvent.ReturnPressed -> navigator.popBackStack()
        }
        sharedPref.setSettings(settings)
    }
}