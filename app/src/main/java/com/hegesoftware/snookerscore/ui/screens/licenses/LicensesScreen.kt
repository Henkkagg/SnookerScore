package com.hegesoftware.snookerscore.ui.screens.licenses

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextDecoration
import com.hegesoftware.snookerscore.ui.components.MediumSpacer
import com.hegesoftware.snookerscore.ui.screens.LicensesTransitions
import com.hegesoftware.snookerscore.ui.theme.padding
import com.ramcosta.composedestinations.annotation.Destination

@Destination(style = LicensesTransitions::class)
@Composable
fun LicensesScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.padding.small)
    ) {
        item {
            val style = MaterialTheme.typography.labelSmall
            val uriHandler = LocalUriHandler.current

            Text("Source code for this app is available at: ",
                style = style)
            Text(
                text = "https://github.com/Henkkagg/SnookerScore/",
                style = style.copy(color = Color.Blue),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    uriHandler.openUri("https://github.com/Henkkagg/SnookerScore/")
                }
            )
            MediumSpacer()
            Text(
                "The license below is applicable to this software and the following libraries:",
                style = style
            )
            Text(
                "-Compose-destinations\nhttps://github.com/raamcosta/compose-destinations",
                style = style
            )
            Text("-Gson\nhttps://github.com/google/gson", style = style)
            MediumSpacer()
            Text(LicenseStrings.apache, style = style)
        }
    }
}