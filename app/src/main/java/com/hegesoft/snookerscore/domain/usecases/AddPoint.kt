package com.hegesoft.snookerscore.domain.usecases

import com.hegesoft.snookerscore.domain.BreakRepository
import com.hegesoft.snookerscore.domain.models.Ball
import com.hegesoft.snookerscore.domain.models.toBreakUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddPoint @Inject constructor(
    private val repository: BreakRepository,
    private val getPlayerInTurn: GetPlayerInTurn,
    private val getLegalBallsStream: GetLegalBallsStream
) {

    suspend operator fun invoke(ball: Ball) = withContext(Dispatchers.IO) {
        val breakDbBefore = repository.getNewest().first()
        val breakPointsBefore = breakDbBefore.toBreakUi().breakPoints
        val playerInTurn = getPlayerInTurn(breakDbBefore)

        val lowestPossiblePoint = getLegalBallsStream().first().lowestValue()
        val freeBallWasPotted = breakPointsBefore.isEmpty() && ball.toInt() != lowestPossiblePoint

        val breakPointsAfter =
            breakPointsBefore + if (freeBallWasPotted) lowestPossiblePoint else ball.toInt()

        val breakDbAfter = breakDbBefore.copy(
            player1Points = if (playerInTurn == 1) breakPointsAfter else breakDbBefore.player1Points,
            player2Points = if (playerInTurn == 2) breakPointsAfter else breakDbBefore.player2Points,
            freeBallStatus = if (freeBallWasPotted) 2 else breakDbBefore.freeBallStatus
        )

        repository.updateCurrent(breakDbAfter)
    }
}