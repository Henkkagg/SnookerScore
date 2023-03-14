package com.hegesoftware.snookerscore.domain.usecases

import com.hegesoftware.snookerscore.data.BreakDb
import javax.inject.Inject

class GetPlayerInTurn @Inject constructor() {

    operator fun invoke(breakDb: BreakDb): Int {

        return if (breakDb.breakId % 2 == 1) 1 else 2
    }
}