package com.hegesoft.snookerscore.domain.usecases

import android.util.Log
import com.hegesoft.snookerscore.data.BreakRepositoryImpl
import com.hegesoft.snookerscore.domain.BreakRepository
import com.hegesoft.snookerscore.domain.models.LegalBalls
import com.hegesoft.snookerscore.domain.models.toBreakUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetLegalBallsStream @Inject constructor(
    private val repository: BreakRepository,
    private val getScoreStream: GetScoreStream
) {

    private sealed class LastPotted {
        object Nothing : LastPotted()
        object Red : LastPotted()
        object NonRed : LastPotted()
    }

    operator fun invoke() = flow {
        repository.getAll().collect { breakList ->

            if (breakList.isEmpty()) return@collect

            var lastPotted: LastPotted = LastPotted.Nothing
            var redsRemaining = 15
            var lowestPointRemaining = 1
            var lastShotStartedPhase2 = false

            for (breakDb in breakList) {
                if (breakDb.redsLost >= 1 && redsRemaining - breakDb.redsLost == 0) {
                    lowestPointRemaining += 1
                }
                redsRemaining -= breakDb.redsLost
                lastPotted = LastPotted.Nothing
                lastShotStartedPhase2 = false

                val breakPoints = breakDb.toBreakUi().breakPoints

                for (pointIndex in breakPoints.indices) {
                    val point = breakPoints[pointIndex]
                    val pointWasFreeBall = pointIndex == 0 && breakDb.freeBallStatus == 2

                    if (pointWasFreeBall) {
                        lastPotted = LastPotted.Red
                        continue
                    }

                    if (redsRemaining > 0) {
                        if (point == 1) {
                            lastPotted = LastPotted.Red
                            redsRemaining -= 1
                            if (redsRemaining == 0) {
                                lastShotStartedPhase2 = true
                                lowestPointRemaining += 1
                            }
                        } else {
                            lastPotted = LastPotted.NonRed
                        }
                    } else {
                        if (lastShotStartedPhase2) {
                            lastShotStartedPhase2 = false
                        } else {
                            val score = getScoreStream().first()

                            //To prevent frame ending in draw, black is put back to table if scores equal
                            if (lowestPointRemaining != 7 || score.player1Score != score.player2Score) {
                                lowestPointRemaining += 1
                            }
                        }
                    }
                }
            }

            val lastBreak = breakList.last()
            val isFirstOfBreak = lastBreak.toBreakUi().breakPoints.isEmpty()

            val isShootingFreeBall = lastBreak.freeBallStatus == 1 && isFirstOfBreak

            val legalBalls = if (isShootingFreeBall || lastShotStartedPhase2) {
                LegalBalls(
                    red = lowestPointRemaining <= 1,
                    yellow = lowestPointRemaining <= 2,
                    green = lowestPointRemaining <= 3,
                    brown = lowestPointRemaining <= 4,
                    blue = lowestPointRemaining <= 5,
                    pink = lowestPointRemaining <= 6,
                    black = lowestPointRemaining <= 7,
                    isFreeBall = isShootingFreeBall
                )
            } else {
                if (redsRemaining > 0) {
                    LegalBalls(
                        //Red is always true because it's legal to pot multiple reds with same shot
                        red = true,
                        yellow = lastPotted == LastPotted.Red,
                        green = lastPotted == LastPotted.Red,
                        brown = lastPotted == LastPotted.Red,
                        blue = lastPotted == LastPotted.Red,
                        pink = lastPotted == LastPotted.Red,
                        black = lastPotted == LastPotted.Red
                    )
                } else {
                    LegalBalls(
                        red = false,
                        yellow = lowestPointRemaining == 2,
                        green = lowestPointRemaining == 3,
                        brown = lowestPointRemaining == 4,
                        blue = lowestPointRemaining == 5,
                        pink = lowestPointRemaining == 6,
                        black = lowestPointRemaining == 7
                    )
                }
            }

            emit(legalBalls)
        }
    }.flowOn(Dispatchers.IO)

}