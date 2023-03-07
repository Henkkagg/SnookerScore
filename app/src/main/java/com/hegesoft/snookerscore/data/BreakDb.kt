package com.hegesoft.snookerscore.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson

@Entity(tableName = "break_table")
@TypeConverters(Converters::class)
data class BreakDb(
    @PrimaryKey(autoGenerate = true)
    val breakId: Int = 0,

    val player1Points: List<Int>,
    val player2Points: List<Int>,
    val redsLost: Int,
    //0=no free ball, 1=free ball was granted but NOT used, 2=free ball was granted AND used
    val freeBallStatus: Int
)

class Converters {
    @TypeConverter
    fun listToString(list: List<Int>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun stringToList(string: String): List<Int> {
        return Gson().fromJson(string, Array<Int>::class.java).toList()
    }
}