package com.hegesoftware.snookerscore.domain.models

data class AlertDialog(
    val titleText: String,
    val bodyText: String,
    val yesText: String,
    val noText: String = "Cancel",
    val onYes: () -> Unit,
    val onNo: () -> Unit
)