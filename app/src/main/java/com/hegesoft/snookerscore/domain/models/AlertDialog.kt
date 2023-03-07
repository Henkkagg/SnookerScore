package com.hegesoft.snookerscore.domain.models

data class AlertDialog(
    val titleText: String,
    val bodyText: String,
    val yesText: String,
    val noText: String = "Keep playing",
    val onYes: () -> Unit,
    val onNo: () -> Unit
)