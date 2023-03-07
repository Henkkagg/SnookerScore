package com.hegesoft.snookerscore.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BreakDao {

    @Insert
    suspend fun insertEmpty(emptyBreakDb: BreakDb)

    @Query("SELECT * FROM break_table ORDER BY breakId DESC LIMIT 1")
    fun getNewest(): Flow<BreakDb>

    @Query("SELECT * FROM break_table ORDER BY breakId ASC")
    fun getAll(): Flow<List<BreakDb>>

    @Update
    suspend fun updateCurrent(updatedBreakDb: BreakDb)

    @Query("DELETE FROM break_table WHERE breakId = (SELECT MAX(breakId) FROM break_table)")
    suspend fun deleteCurrent()

    @Query("DELETE FROM break_table")
    suspend fun deleteAll()

    @Query("UPDATE SQLITE_SEQUENCE SET seq = 0 WHERE name = 'break_table'")
    suspend fun resetAutoIncrement()
}