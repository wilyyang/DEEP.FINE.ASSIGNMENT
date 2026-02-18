package com.deepfine.assignment.core.feature.viewmodel

import android.widget.Toast
import com.deepfine.assignment.core.common.util.UiText

sealed class CommonEffect : ViewSideEffect {
    data class ShowToast(val message: UiText, val duration: Int = Toast.LENGTH_SHORT) : CommonEffect()
    sealed class Navigation : CommonEffect() {
        data object NavigateBack : Navigation()
    }
}