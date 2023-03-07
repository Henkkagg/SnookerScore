package com.hegesoft.snookerscore.domain

import com.hegesoft.snookerscore.domain.usecases.*
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
    val undo: Undo
)