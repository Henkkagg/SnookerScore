package com.hegesoft.snookerscore.ui.screens.new_game

sealed class NewGameUiEvent {
    data class Player1Changed(val value: String) : NewGameUiEvent()
    data class Player2Changed(val value: String) : NewGameUiEvent()
    object GoToFramePressed : NewGameUiEvent()

    object DeleteGamePressed : NewGameUiEvent()
    object CancelDeleteGamePressed : NewGameUiEvent()
    object ConfirmDeleteGamePressed : NewGameUiEvent()
}