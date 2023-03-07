package com.hegesoft.snookerscore.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BreakDb::class], version = 1)
abstract class BreakDatabase: RoomDatabase() {
    abstract fun breakDao(): BreakDao
}