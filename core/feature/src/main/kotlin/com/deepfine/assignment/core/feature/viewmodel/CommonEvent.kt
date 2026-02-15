package com.deepfine.assignment.core.feature.viewmodel

sealed class CommonEvent : ViewEvent {
    data object OnCreate : CommonEvent()
    data object OnResume : CommonEvent()
    data object OnPause : CommonEvent()
    data object OnDestroy : CommonEvent()

    data object CloseEvent : CommonEvent()
}