package com.hegesoftware.snookerscore.domain.models

import com.hegesoftware.snookerscore.data.BreakDb

data class BreakUi(
    val breakNumber: Int = 0,
    val playerInTurn: Int = 0,
    val breakPoints: List<Int> = emptyList(),
    val firstWasFreeBall: Boolean = false,
    val foulPoints: Int? = null
)

fun BreakDb.toBreakUi(): BreakUi {
    val playerInTurn = if (this.breakId % 2 == 1) 1 else 2
    val breakPoints = if (playerInTurn == 1) player1Points else player2Points
    val foulPoints = (if (playerInTurn == 1) player2Points else player1Points).firstOrNull()

    return BreakUi(
        breakNumber = breakId,
        playerInTurn = playerInTurn,
        breakPoints = breakPoints,
        firstWasFreeBall = freeBallStatus == 2,
        foulPoints = foulPoints
    )
}

fun Int.toOrdinalString(): String {
    val ordinalEnding = when (this % 10) {
        1 -> "st"
        2 -> "nd"
        3 -> "rd"
        else -> "th"
    }

    return this.toString() + ordinalEnding
}
