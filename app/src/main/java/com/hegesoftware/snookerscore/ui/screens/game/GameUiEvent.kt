package com.hegesoftware.snookerscore.ui.screens.game

import com.hegesoftware.snookerscore.domain.models.Ball

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