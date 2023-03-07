package com.hegesoft.snookerscore.ui.screens.game

import com.hegesoft.snookerscore.domain.models.Ball
import com.hegesoft.snookerscore.domain.models.Foul
import com.hegesoft.snookerscore.ui.components.Ball

sealed class GameUiEvent {
    object MenuPressed : GameUiEvent()
    object FoulPressed : GameUiEvent()
    object EndBreakPressed : GameUiEvent()
    object UndoPressed : GameUiEvent()
    data class BallPressed(val ball: Ball) : GameUiEvent()

    data class PenaltyPressed(val penaltyPoints: Int) : GameUiEvent()
    object DecreaseRedsLostPressed : GameUiEvent()
    object IncreaseRedsLostpressed : GameUiEvent()
    object FreeBallPressed : GameUiEvent()
    object ConfirmFoulPressed : GameUiEvent()

    object DownSwiped : GameUiEvent()
    object UpSwiped : GameUiEvent()

    object BackPressed : GameUiEvent()
    object EndFramePressed : GameUiEvent()
}