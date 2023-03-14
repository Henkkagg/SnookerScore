package com.hegesoftware.snookerscore.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.hegesoftware.snookerscore.domain.models.Ball

@Composable
fun Ball(
    modifier: Modifier = Modifier,
    ball: Ball,
    ballCount: Int? = null,
    isFreeBall: Boolean = false
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
        if (ballCount != null || isFreeBall) {
            val text = ballCount?.toString() ?: "F"

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(100))
                    .background(MaterialTheme.colorScheme.background),
            ) {
                var height by remember { mutableStateOf(0) }
                Text(
                    text = text,
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