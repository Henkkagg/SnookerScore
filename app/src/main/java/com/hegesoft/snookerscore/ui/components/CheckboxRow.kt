package com.hegesoft.snookerscore.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CheckboxRow(
    settingName: String,
    settingExplanation: String,
    isChecked: Boolean,
    onCheckedChange: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = settingName,
                style = MaterialTheme.typography.displayMedium,
            )
            Text(
                text = settingExplanation,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.offset(y = (-8).dp)
            )
        }
        Checkbox(
            checked = isChecked,
            onCheckedChange = { onCheckedChange() },
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primaryContainer,
                uncheckedColor = MaterialTheme.colorScheme.onBackground,
                checkmarkColor = MaterialTheme.colorScheme.onBackground
            )
        )

    }
}