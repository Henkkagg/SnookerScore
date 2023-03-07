package com.hegesoft.snookerscore.domain

import com.hegesoft.snookerscore.data.BreakDb
import kotlinx.coroutines.flow.Flow

interface BreakRepository {

    suspend fun addNew(freeBall: Boolean = false)

    fun getNewest(): Flow<BreakDb>

    fun getAll(): Flow<List<BreakDb>>

    suspend fun updateCurrent(updatedBreakDb: BreakDb)

    suspend fun deleteCurrent()

    suspend fun deleteAll()
}
