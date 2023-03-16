package com.hegesoftware.snookerscore.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    label: String,
    value: String,
    keyboardAction: KeyboardAction,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        label = { Text(text = label) },
        value = value,
        onValueChange = onValueChanged,
        shape = ShapeDefaults.Medium,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            KeyboardCapitalization.Words,
            imeAction = keyboardAction.imeAction
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

sealed class KeyboardAction(val imeAction: ImeAction) {
    object GoNext : KeyboardAction(ImeAction.Next)
    object Finish : KeyboardAction(ImeAction.Go)
}