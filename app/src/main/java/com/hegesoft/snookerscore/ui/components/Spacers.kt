package com.hegesoft.snookerscore.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TinyVerticalSpacer() {
    Spacer(modifier = Modifier.height(5.dp))
}

@Composable
fun TinyHorizontalSpacer() {
    Spacer(modifier = Modifier.width(5.dp))
}

@Composable
fun TinySpacer() {
    Spacer(modifier = Modifier.height(5.dp))
}

@Composable
fun SmallSpacer() {
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun MediumSpacer() {
    Spacer(modifier = Modifier.height(30.dp))
}
