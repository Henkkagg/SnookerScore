package com.hegesoft.snookerscore.domain.usecases

import android.util.Log
import com.hegesoft.snookerscore.domain.BreakRepository
import com.hegesoft.snookerscore.domain.models.toBreakUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Undo @Inject constructor(
    private val repository: BreakRepository,
    private val getPlayerInTurn: GetPlayerInTurn
) {

    /*
    Undo things with this priority (only one is done by ui action):
    1. If last shot waa a foul, undo it and delete the new empty break
    2. If last shot was legal and was in current break, undo the shot
    3. Delete new empty break, e.g. cancel ending the break
     */

    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        val breakDbList = repository.getAll().first()
        val currentBreakDb = breakDbList.last()
        val breakDbWithPossibleFoul = if (breakDbList.size > 1) {
            breakDbList[breakDbList.lastIndex - 1]
        } else {
            null
        }

        val currentBreakUi = currentBreakDb.toBreakUi()
        val breakUiWithPossibleFoul = breakDbWithPossibleFoul?.toBreakUi()
        val breakPoints = currentBreakUi.breakPoints
        val playerInTurnNow = getPlayerInTurn(currentBreakDb)

        val foulToBeUndone = breakUiWithPossibleFoul?.foulPoints != null && breakPoints.isEmpty()
        if (foulToBeUndone) {
            val playerInTurnBefore = getPlayerInTurn(breakDbWithPossibleFoul!!)
            val newBreakDb = breakDbWithPossibleFoul.copy(
                player1Points = if (playerInTurnBefore == 1) breakDbWithPossibleFoul.player1Points else emptyList(),
                player2Points = if (playerInTurnBefore == 2) breakDbWithPossibleFoul.player2Points else emptyList(),
                redsLost = 0,
                freeBallStatus = 0
            )

            //Making foul creates a new empty break that needs to be deleted first
            repository.deleteCurrent()
            repository.updateCurrent(newBreakDb)
            return@withContext
        }

        val shotToBeUndone = breakPoints.isNotEmpty()
        val freeBallWasPotted = currentBreakDb.freeBallStatus == 2 && breakPoints.size == 1
        if (shotToBeUndone) {
            val newBreakPoints = breakPoints.dropLast(1)
            val newBreakDb = currentBreakDb.copy(
                player1Points = if (playerInTurnNow == 1) newBreakPoints else currentBreakDb.player1Points,
                player2Points = if (playerInTurnNow == 2) newBreakPoints else currentBreakDb.player2Points,
                freeBallStatus = if (freeBallWasPotted) 1 else currentBreakDb.freeBallStatus
            )

            repository.updateCurrent(newBreakDb)
            return@withContext
        }

        if (breakDbList.size > 1) repository.deleteCurrent()
    }
}