package com.hegesoftware.snookerscore.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.hegesoftware.snookerscore.domain.models.Ball
import com.hegesoftware.snookerscore.ui.theme.padding

@Composable
fun CurrentBreak(
    topText: String,
    balls: List<Ball>,
    bottomText: String,
    isFreeBall: Boolean,
    firstWasFreeBall: Boolean
) {
    Surface(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.padding.small),
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
            if (isFreeBall && balls.isEmpty()) {
                Text(
                    text = "Free ball",
                    style = MaterialTheme.typography.displayMedium,
                    modifier = modifier
                )

            } else {
                LazyRow(
                    state = lazyListState,
                    modifier = modifier.padding(horizontal = MaterialTheme.padding.tiny)
                ) {
                    itemsIndexed(balls) { index, ball ->
                        Ball(
                            ball = ball,
                            isFreeBall = firstWasFreeBall && index == 0
                        )
                    }
                }
            }
            Text(bottomText)
        }
    }
}
