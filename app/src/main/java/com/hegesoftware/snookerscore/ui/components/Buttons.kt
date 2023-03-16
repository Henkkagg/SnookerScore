package com.hegesoftware.snookerscore.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.hegesoftware.snookerscore.domain.models.Ball
import com.hegesoftware.snookerscore.domain.models.Settings
import com.hegesoftware.snookerscore.ui.theme.padding

@Composable
fun WideButton(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    onPressed: () -> Unit
) {

    Button(
        onClick = onPressed,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.padding.small)
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            modifier = Modifier.padding(vertical = 3.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BallButton(
    modifier: Modifier = Modifier,
    ball: Ball,
    ballCount: Int? = null,
    enabled: Boolean,
    onPressed: () -> Unit
) {
    Card(
        shape = CircleShape,
        modifier = modifier,
        enabled = enabled,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        ),
        onClick = onPressed
    ) {
        if (enabled) {
            Ball(
                ball = ball,
                ballCount = ballCount
            )
        } else BallEmptyPlaceHolder()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String,
    onPressed: () -> Unit
) {

    Card(
        shape = CircleShape,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = modifier,
        onClick = onPressed
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription,
                modifier = Modifier
                    .fillMaxSize(0.5f)
                    //Need to mirror refresh icon to look like reverse icon. The other icon is horizontially symmetric
                    .scale(scaleX = -1f, scaleY = 1f)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isSelected: Boolean = false,
    onPressed: () -> Unit,
    content: @Composable () -> Unit
) {
    val border = BorderStroke(
        width = 2.dp,
        color = if (enabled) MaterialTheme.colorScheme.onBackground else Color.Transparent
    )
    val colors = CardDefaults.cardColors(
        containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        disabledContainerColor = Color.Transparent,
        disabledContentColor = MaterialTheme.colorScheme.onBackground
    )

    Card(
        shape = RoundedCornerShape(10.dp),
        border = border,
        colors = colors,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f, true),
        onClick = onPressed
    ) {
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharButton(
    modifier: Modifier = Modifier,
    char: Char,
    isSelected: Boolean,
    isVisible: Boolean,
    onPressed: () -> Unit
) {
    var isEnabled by remember { mutableStateOf(isVisible && !isSelected) }
    LaunchedEffect(isVisible && !isSelected) {
        isEnabled = isVisible && !isSelected
    }

    val border = BorderStroke(
        width = 2.dp,
        color = if (isVisible) MaterialTheme.colorScheme.onBackground else Color.Transparent
    )

    val colors = CardDefaults.cardColors(
        containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
        disabledContainerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
        disabledContentColor = MaterialTheme.colorScheme.onBackground
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f, true),
        shape = RoundedCornerShape(10.dp),
        enabled = isVisible && !isSelected,
        border = border,
        colors = colors,
        onClick = onPressed
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isVisible) {
                Text(
                    text = char.toString(),
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }
    }
}

/*
Problem: detecting drag gestures consumes the initial button press. If the user presses the button in a
"dragging" motion, nothing happens, as it's not a proper drag gesture, but regardless the press is consumed too.
Draggy button presses are normal for users, as the app is generally operated from awkward angle
when phone is placed on a surface

Fix: if user pressed a button in a "dragging" motion, but didn't do a real drag gesture, execute the onSloppyPress lambda
 */
fun Modifier.onDrag(
    onDownSwipe: () -> Unit = { },
    onUpSwipe: () -> Unit = { },
    onSloppyPress: () -> Unit = { },
    settings: Settings,
): Modifier {
    var yStart = 0f
    var yEnd = 0f

    return this.pointerInput(settings) {

        detectDragGestures(
            onDragStart = {
                yStart = it.y
                yEnd = it.y
            },
            onDragEnd = {
                when {
                    yEnd - yStart > 300f -> if (settings.swipingEnabled) onDownSwipe()
                    yEnd - yStart < -300f -> if (settings.swipingEnabled) onUpSwipe()
                    else -> if (settings.sloppyPressEnabled) onSloppyPress()
                }
            },
            onDrag = { _, change ->
                yEnd += change.y
            }
        )
    }
}