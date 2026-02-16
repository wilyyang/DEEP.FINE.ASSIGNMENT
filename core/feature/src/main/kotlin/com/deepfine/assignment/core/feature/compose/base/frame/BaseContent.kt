package com.deepfine.assignment.core.feature.compose.base.frame

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun BaseContent(
    modifier: Modifier = Modifier,
    initShowState: InitShowState,
    initContent: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        if (initShowState != InitShowState.ScreenAnimationDelay) {
            AnimatedVisibility(
                visible = initShowState == InitShowState.InitFadingOut || initShowState == InitShowState.None,
                enter = fadeIn(animationSpec = tween(durationMillis = ANIMATION_INIT_SCREEN_FADE_OUT_MS)),
                exit = ExitTransition.None
            ) {
                Box(modifier = Modifier.fillMaxSize()) {

                    content()

                    if (initShowState != InitShowState.None) {
                        Box(modifier = Modifier.fillMaxSize().pointerInput(Unit) {})
                    }
                }
            }
        }

        val alpha by animateFloatAsState(
            targetValue = if (initShowState == InitShowState.InitFadingOut) 0.0f else 1f,
            animationSpec = tween(durationMillis = ANIMATION_INIT_SCREEN_FADE_OUT_MS),
            label = "initContentAlpha"
        )

        if (initShowState != InitShowState.None && initContent != null) {
            Box(modifier = Modifier.alpha(alpha)) {
                initContent()
            }
        }
    }
}