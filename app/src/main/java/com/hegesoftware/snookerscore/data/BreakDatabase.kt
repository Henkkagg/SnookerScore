package com.hegesoftware.snookerscore.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BreakDb::class], version = 1, exportSchema = false)
abstract class BreakDatabase: RoomDatabase() {
    abstract fun breakDao(): BreakDao
}