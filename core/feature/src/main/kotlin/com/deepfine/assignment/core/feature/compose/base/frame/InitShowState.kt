package com.deepfine.assignment.core.feature.compose.base.frame

import com.deepfine.assignment.core.feature.viewmodel.OverlayState

const val DELAY_SCREEN_ANIMATION_MS = 330L
const val DELAY_INIT_SCREEN_FADE_OUT_MS = 800L
const val ANIMATION_INIT_SCREEN_FADE_OUT_MS = 700
enum class InitShowState {
    ScreenAnimationDelay,
    InitShow,
    InitFadingOut,
    None;

    fun transState(state: OverlayState): InitShowState {
        return when (this) {
            InitShow -> {
                when (state) {
                    is OverlayState.Init -> InitShow
                    else -> InitFadingOut
                }
            }
            else -> this
        }
    }
}