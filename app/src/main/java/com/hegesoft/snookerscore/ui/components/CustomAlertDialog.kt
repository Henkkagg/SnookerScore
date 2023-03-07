package com.hegesoft.snookerscore.ui.components

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun CustomAlertDialog(
    titleText: String,
    yesText: String,
    noText: String,
    bodyText: String,
    onYes: () -> Unit,
    onNo: () -> Unit
) {
    Dialog(
        onDismissRequest = onNo,
        properties = DialogProperties()
    ) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.onBackground),
            modifier = Modifier.clip(RoundedCornerShape(10.dp))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = titleText,
                    style = MaterialTheme.typography.displayMedium
                )
                MediumSpacer()
                Text(
                    text = bodyText,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                MediumSpacer()
                Row {
                    val modifier = Modifier.weight(1f)
                    WideButton(
                        text = yesText,
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        onPressed = onYes,
                        modifier = modifier
                    )
                    WideButton(
                        text = noText,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        onPressed = onNo,
                        modifier = modifier
                    )
                }
            }
        }
    }
}