package com.deepfine.assignment.feature.auth.signup

import com.deepfine.assignment.core.feature.viewmodel.ViewEvent
import com.deepfine.assignment.core.feature.viewmodel.ViewSideEffect
import com.deepfine.assignment.core.feature.viewmodel.ViewState

class SignUpContract {
    companion object {
        const val NAME = "signup"
    }

    data class State(
        val isInitSuccess: Boolean = false
    ) : ViewState

    sealed class Event : ViewEvent

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect()
    }
}