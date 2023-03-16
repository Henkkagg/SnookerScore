package com.hegesoftware.snookerscore.ui.screens.settings

sealed class SettingsUiEvent {
    object DragPressed : SettingsUiEvent()
    object HideButtonsPressed : SettingsUiEvent()
    object SloppyPressed : SettingsUiEvent()
    object ShowRedsPressed : SettingsUiEvent()

    object ReturnPressed : SettingsUiEvent()
}