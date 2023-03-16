package com.hegesoftware.snookerscore.domain

import com.hegesoftware.snookerscore.domain.usecases.*
import javax.inject.Inject

data class BreakUsecases @Inject constructor(
    val newGame: NewGame,
    val newBreak: NewBreak,
    val getBreakStream: GetBreakStream,
    val getScoreStream: GetScoreStream,
    val getLegalBallsStream: GetLegalBallsStream,
    val getPlayerInTurn: GetPlayerInTurn,
    val addPoint: AddPoint,
    val addFoul: AddFoul,
    val undo: Undo,
)