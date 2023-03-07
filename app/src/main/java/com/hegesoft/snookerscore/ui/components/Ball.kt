package com.hegesoft.snookerscore.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hegesoft.snookerscore.domain.models.Ball

@Composable
fun Ball(
    modifier: Modifier = Modifier,
    ball: Ball,
    ballCount: Int? = null
) {
    val color = remember {
        Brush.radialGradient(
            colors = listOf(Color.White, ball.color),
            center = Offset(Float.POSITIVE_INFINITY, 0f)
        )
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .clip(CircleShape)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        if (ballCount != null) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(100))
                    .background(MaterialTheme.colorScheme.background),
            ) {
                var height by remember { mutableStateOf(0) }
                Text(
                    text = "$ballCount",
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .onGloballyPositioned {
                            height = it.size.height
                        }
                        .width(
                            with(LocalDensity.current) {
                                height.toDp()
                            }
                        )
                )
            }
        }
    }
}

@Composable
fun BallEmptyPlaceHolder() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .clip(CircleShape)
    )
}