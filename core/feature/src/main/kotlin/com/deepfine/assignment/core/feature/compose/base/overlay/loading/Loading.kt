package com.deepfine.assignment.core.feature.compose.base.overlay.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Popup
import kotlinx.coroutines.delay

const val DELAY_LOADING_SHOW_MS = 300L

@Composable
fun Loading(
    delayMillis: Long = DELAY_LOADING_SHOW_MS,
    onDismiss: () -> Unit = {}
) {
    val isShowAnimation = remember { mutableStateOf(false) }
    LaunchedEffect(key1 = Unit) {
        delay(delayMillis)
        isShowAnimation.value = true
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Color.Transparent) {
        if (isShowAnimation.value) {
            Box(modifier = Modifier.fillMaxSize()) {
                Popup(
                    onDismissRequest = onDismiss
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        RotatingDonutAnimation()
                    }
                }
            }
        }
    }
}