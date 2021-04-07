package com.waqar.sampleproject.core.model

open class AlertModel constructor(
    val title: String? = "",
    val message: String? = "",
    val positiveButtonTitle: String? = "",
    val onPositiveButtonClickAction: (() -> Unit)? = null,
    val negativeButtonTitle: String? = "",
    val onNegativeButtonClickAction: (() -> Unit)? = null
)