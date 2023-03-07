package com.hegesoft.snookerscore.domain.models

data class Foul(
    val penaltyPoints: Int = 4,
    val redsLost: Int = 0,
    val isFreeBall: Boolean = false
)
