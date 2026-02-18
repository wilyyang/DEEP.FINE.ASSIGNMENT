package com.deepfine.assignment.feature.auth.login

import com.deepfine.assignment.core.common.util.UiText
import com.deepfine.assignment.core.feature.viewmodel.ViewEvent
import com.deepfine.assignment.core.feature.viewmodel.ViewSideEffect
import com.deepfine.assignment.core.feature.viewmodel.ViewState
import com.deepfine.assignment.feature.auth.R

class LoginContract {
    companion object {
        const val NAME = "login"
    }

    sealed class Step(
        open val topTitle: UiText,
        open val topMessage: UiText,
        open val bottomButton: UiText
    ) {
        data object Start : Step(
            topTitle = UiText.StringResource(R.string.login_top_title_start_step),
            topMessage = UiText.StringResource(R.string.login_top_message_start_step),
            bottomButton = UiText.StringResource(R.string.login_bottom_button_start_step)
        )
        data object LoginPassword : Step(
            topTitle = UiText.StringResource(R.string.login_top_title_login_step),
            topMessage = UiText.StringResource(R.string.login_top_message_login_step),
            bottomButton = UiText.StringResource(R.string.login_bottom_button_login_step)
        )
        data object Signup : Step(
            topTitle = UiText.StringResource(R.string.login_top_title_signup_step),
            topMessage = UiText.Empty,
            bottomButton = UiText.StringResource(R.string.login_bottom_button_signup_step)
        )
    }

    sealed interface EmailValidity {
        data object Unknown : EmailValidity
        data object Valid : EmailValidity
        data object Invalid : EmailValidity
    }

    data class State(
        val step: Step = Step.Start,

        val email: String = "",
        val password: String = "",

        val emailValidity: EmailValidity = EmailValidity.Unknown
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnEmailChanged(val text: String) : Event()
        data class OnPasswordChanged(val text: String) : Event()
        data object OnClickBottom : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class NavigateSignupScreen(val email: String) : Navigation()
        }
    }
}