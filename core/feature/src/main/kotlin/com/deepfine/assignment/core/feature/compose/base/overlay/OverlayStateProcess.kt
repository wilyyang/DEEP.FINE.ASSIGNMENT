package com.deepfine.assignment.core.feature.compose.base.overlay

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.deepfine.assignment.core.feature.compose.base.overlay.dialog.CommonDialog
import com.deepfine.assignment.core.feature.compose.base.overlay.loading.Loading
import com.deepfine.assignment.core.feature.viewmodel.OverlayState

@Composable
fun OverlayStateProcess(
    state: OverlayState,
    showDefaultLoadingIndicator: Boolean
) {
    val context = LocalContext.current
    when(state){
        is OverlayState.Loading -> {
            if (showDefaultLoadingIndicator) {
                Loading()
            } else {
                Surface(modifier = Modifier.fillMaxSize(), color = Color.Transparent) {}
            }
        }
        is OverlayState.CommonDialog -> {
            CommonDialog(
                title = state.title?.asString(context),
                message = state.message?.asString(context),
                confirmText = state.confirmText?.asString(context),
                dismissText = state.dismissText?.asString(context),
                onConfirm = state.onConfirm,
                onDismiss = state.onDismiss
            )
        }
        else -> {}
    }
}