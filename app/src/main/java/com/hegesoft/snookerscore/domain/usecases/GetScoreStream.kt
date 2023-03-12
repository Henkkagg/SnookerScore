package com.hegesoft.snookerscore.domain.usecases

import android.util.Log
import com.hegesoft.snookerscore.domain.BreakRepository
import com.hegesoft.snookerscore.domain.models.Score
import com.hegesoft.snookerscore.domain.models.toBreakUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Integer.max
import javax.inject.Inject

class GetScoreStream @Inject constructor(private val repository: BreakRepository) {

    operator fun invoke() = flow {

        repository.getAll().collect { breakList ->

            if (breakList.isEmpty()) return@collect

            var player1Score = 0
            var player2Score = 0

            breakList.forEach { breakDb ->
                player1Score += breakDb.player1Points.sum()
                player2Score += breakDb.player2Points.sum()
            }

            var redsRemaining = 15
            var lowestPointRemaining = 1
            var lastShotStartedPhase2 = false

            for (breakDb in breakList) {
                if (breakDb.redsLost >= 1 && redsRemaining - breakDb.redsLost == 0) {
                    lowestPointRemaining += 1
                }
                redsRemaining -= breakDb.redsLost
                lastShotStartedPhase2 = false

                val breakPoints = breakDb.toBreakUi().breakPoints
                for (pointIndex in breakPoints.indices) {
                    val point = breakPoints[pointIndex]
                    val pointWasFreeBall = pointIndex == 0 && breakDb.freeBallStatus == 2

                    if (pointWasFreeBall) continue

                    if (redsRemaining > 0) {
                        if (point == 1) {
                            redsRemaining -= 1
                            if (redsRemaining == 0) {
                                lastShotStartedPhase2 = true
                                lowestPointRemaining += 1
                            }
                        }
                    } else {
                        if (lastShotStartedPhase2) {
                            lastShotStartedPhase2 = false
                        } else {
                            lowestPointRemaining += 1
                        }
                    }
                }
            }

            val lastBreakDb = breakList.last()

            val breakPoints = lastBreakDb.toBreakUi().breakPoints

            var pointsOnTable = 8 * redsRemaining + 27
            if (breakPoints.isNotEmpty() && breakPoints.last() == 1) pointsOnTable += 7
            for (i in 2 until lowestPointRemaining) {
                pointsOnTable -= i
            }

            val isShootingFreeBall = breakPoints.isEmpty() && lastBreakDb.freeBallStatus == 1
            if (isShootingFreeBall) {
                pointsOnTable += if (redsRemaining > 0) 8 else lowestPointRemaining
            }

            val score = Score(
                player1Score = player1Score,
                player2Score = player2Score,
                pointsOnTable = max(pointsOnTable, 0),
                redsRemaining = redsRemaining
            )

            emit(score)
        }
    }.flowOn(Dispatchers.IO)
}