package com.hegesoft.snookerscore.domain.usecases

import com.hegesoft.snookerscore.data.BreakDb
import com.hegesoft.snookerscore.domain.BreakRepository
import javax.inject.Inject

class GetPlayerInTurn @Inject constructor() {

    operator fun invoke(breakDb: BreakDb): Int {

        return if (breakDb.breakId % 2 == 1) 1 else 2
    }
}