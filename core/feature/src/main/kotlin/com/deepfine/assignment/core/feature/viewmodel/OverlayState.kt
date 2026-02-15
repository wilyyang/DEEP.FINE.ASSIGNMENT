package com.deepfine.assignment.core.feature.viewmodel

import com.deepfine.assignment.core.common.util.UiText

sealed class OverlayState {
    data object Init : OverlayState()
    data object Idle : OverlayState()
    data object Close : OverlayState()
    data object Loading : OverlayState()
    data class CommonDialog(
        val title: UiText? = null,
        val message: UiText? = null,
        val confirmText: UiText? = UiText.Empty,
        val dismissText: UiText? = UiText.Empty,
        val onConfirm: () -> Unit = {},
        val onDismiss: () -> Unit = {}
    ) : OverlayState() {
        constructor(
            title: String? = null,
            message: String? = null,
            confirmText: String? = "",
            dismissText: String? = "",
            onConfirm: () -> Unit = {},
            onDismiss: () -> Unit = {}
        ) : this(
            title = title?.let { UiText.DynamicString(title) },
            message = message?.let { UiText.DynamicString(message) },
            confirmText = confirmText?.let { UiText.DynamicString(confirmText) },
            dismissText = dismissText?.let { UiText.DynamicString(dismissText) },
            onConfirm = onConfirm,
            onDismiss = onDismiss
        )
    }
}