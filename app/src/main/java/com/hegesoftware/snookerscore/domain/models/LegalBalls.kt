package com.hegesoftware.snookerscore.domain.models

data class LegalBalls(
    val red: Boolean = false,
    val yellow: Boolean = false,
    val green: Boolean = false,
    val brown: Boolean = false,
    val blue: Boolean = false,
    val pink: Boolean = false,
    val black: Boolean = false,
    val isFreeBall: Boolean = false
) {
    fun lowestValue() = when {
        red -> 1
        yellow -> 2
        green -> 3
        brown -> 4
        blue -> 5
        pink -> 6
        black -> 7
        else -> 404
    }
}
