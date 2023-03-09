package com.hegesoft.snookerscore.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Padding(
    val tiny: Dp = 5.dp,
    val small: Dp = 10.dp,
    val large: Dp = 20.dp
)

val LocalPadding = compositionLocalOf { Padding() }

val MaterialTheme.padding
@Composable
@ReadOnlyComposable
get() = LocalPadding.current