package com.hegesoft.snookerscore.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hegesoft.snookerscore.domain.models.Ball

@Composable
fun CurrentBreak(
    topText: String,
    balls: List<Ball>,
    bottomText: String,
    isFreeball: Boolean
) {
    Surface(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth(),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primaryContainer),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(topText)

            val lazyListState = rememberLazyListState()
            LaunchedEffect(balls) {
                if (balls.isNotEmpty()) lazyListState.scrollToItem(balls.lastIndex)
            }

            val modifier = Modifier.weight(1f, true)
            if (isFreeball) {
                Text(
                    text = "Free ball",
                    style = MaterialTheme.typography.displayMedium,
                    modifier = modifier
                )

            } else {
                LazyRow(
                    state = lazyListState,
                    modifier = modifier
                ) {
                    items(balls) { ball ->
                        Ball(ball = ball)
                    }
                }
            }

            Text(bottomText)
        }
    }
}
