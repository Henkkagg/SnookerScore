package com.hegesoft.snookerscore.domain.models

import androidx.compose.ui.graphics.Color

sealed class Ball(val color: Color) {
    object Red : Ball(Color(0xFFcf1111))
    object Yellow : Ball(Color(0xFFDABF15))
    object Green : Ball(Color(0xFF0a6e0a))
    object Brown : Ball(Color(0xFF643A19))
    object Blue : Ball(Color(0xFF1919B4))
    object Pink : Ball(Color(0xFFC90BBC))
    object Black : Ball(Color(0xFF0E0E0E))

    fun toInt(): Int {
        return when (this) {
            Red -> 1
            Yellow -> 2
            Green -> 3
            Brown -> 4
            Blue -> 5
            Pink -> 6
            Black -> 7
        }
    }
}

fun List<Int>.toBalls() = map { numericValue ->
    when (numericValue) {
        1 -> Ball.Red
        2 -> Ball.Yellow
        3 -> Ball.Green
        4 -> Ball.Brown
        5 -> Ball.Blue
        6 -> Ball.Pink
        else -> Ball.Black
    }
}