package com.hegesoftware.snookerscore.domain.usecases

import com.hegesoftware.snookerscore.domain.BreakRepository
import com.hegesoftware.snookerscore.domain.models.Foul
import com.hegesoftware.snookerscore.domain.models.toBreakUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddFoul @Inject constructor(private val repository: BreakRepository) {

    suspend operator fun invoke(foul: Foul) = withContext(Dispatchers.IO) {

        val breakDb = repository.getNewest().first()
        val playerInTurn = breakDb.toBreakUi().playerInTurn

        val breakDbWithFoul = breakDb.copy(
            player1Points = if (playerInTurn == 2) listOf(foul.penaltyPoints) else breakDb.player1Points,
            player2Points = if (playerInTurn == 1) listOf(foul.penaltyPoints) else breakDb.player2Points,
            redsLost = foul.redsLost
        )

        repository.updateCurrent(breakDbWithFoul)
        repository.addNew(freeBall = foul.isFreeBall)
    }
}