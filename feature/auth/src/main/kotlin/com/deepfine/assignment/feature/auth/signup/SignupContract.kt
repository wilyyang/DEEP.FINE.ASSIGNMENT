package com.deepfine.assignment.feature.auth.signup

import com.deepfine.assignment.core.common.util.UiText
import com.deepfine.assignment.core.feature.viewmodel.ViewEvent
import com.deepfine.assignment.core.feature.viewmodel.ViewSideEffect
import com.deepfine.assignment.core.feature.viewmodel.ViewState
import com.deepfine.assignment.feature.auth.R

class SignupContract {
    companion object {
        const val NAME = "signup"
    }

    sealed class Step(
        open val order: Int,
        open val topTitle: UiText,
        open val topMessage: UiText,
        open val bottomButton: UiText
    ) {
        data object Name : Step(
            order = 1,
            topTitle = UiText.StringResource(R.string.signup_top_title_name_step),
            topMessage = UiText.StringResource(R.string.signup_top_message_name_step),
            bottomButton = UiText.StringResource(R.string.signup_bottom_button_name_step)
        )

        data object Password : Step(
            order = 2,
            topTitle = UiText.StringResource(R.string.signup_top_title_password_step),
            topMessage = UiText.StringResource(R.string.signup_top_message_password_step),
            bottomButton = UiText.StringResource(R.string.signup_bottom_button_password_step)
        )

        data object Complete : Step(
            order = 3,
            topTitle = UiText.StringResource(R.string.signup_top_title_complete),
            topMessage = UiText.StringResource(R.string.signup_top_message_complete),
            bottomButton = UiText.StringResource(R.string.signup_bottom_button_complete)
        )
    }

    data class State(
        val step: Step = Step.Name,
        val name: String = "",
        val password: String = ""
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnNameChanged(val text: String) : Event()
        data class OnPasswordChanged(val text: String) : Event()
        data object OnClickBottom : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data object NavigateLoginScreen : Navigation()
        }
    }
}