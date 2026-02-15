package com.deepfine.assignment.core.feature.viewmodel

sealed class CommonEffect : ViewSideEffect {
    sealed class Navigation : CommonEffect() {
        data object NavigateBack : Navigation()
    }
}