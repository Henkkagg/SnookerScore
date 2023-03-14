package com.hegesoftware.snookerscore.data

import com.hegesoftware.snookerscore.domain.BreakRepository
import javax.inject.Inject

class BreakRepositoryImpl @Inject constructor(private val database: BreakDatabase): BreakRepository {
    private val dao = database.breakDao()

    override suspend fun addNew(freeBall: Boolean) {

        val emptyBreakDb = BreakDb(
            player1Points = emptyList(),
            player2Points = emptyList(),
            redsLost = 0,
            freeBallStatus = if (freeBall) 1 else 0
        )
        dao.insertEmpty(emptyBreakDb)
    }

    override fun getNewest() = dao.getNewest()

    override fun getAll() = dao.getAll()

    override suspend fun updateCurrent(updatedBreakDb: BreakDb) {

        dao.updateCurrent(updatedBreakDb)
    }

    override suspend fun deleteCurrent() {

        dao.deleteCurrent()
        dao.resetAutoIncrement()
    }

    override suspend fun deleteAll() {

        dao.deleteAll()
        dao.resetAutoIncrement()
    }
}