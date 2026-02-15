package com.deepfine.assignment.feature.auth.login

import com.deepfine.assignment.core.feature.viewmodel.ViewEvent
import com.deepfine.assignment.core.feature.viewmodel.ViewSideEffect
import com.deepfine.assignment.core.feature.viewmodel.ViewState

class LoginContract {
    companion object {
        const val NAME = "login"
    }

    data class State(
        val isInitSuccess: Boolean = false
    ) : ViewState

    sealed class Event : ViewEvent

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect()
    }
}