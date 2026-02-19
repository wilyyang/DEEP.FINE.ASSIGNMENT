package com.deepfine.assignment.feature.auth.login

import androidx.lifecycle.SavedStateHandle
import com.deepfine.assignment.core.common.util.UiText
import com.deepfine.assignment.core.feature.viewmodel.BaseViewModel
import com.deepfine.assignment.core.feature.viewmodel.CommonEffect
import com.deepfine.assignment.domain.usecase.auth.UseCaseIsEmailRegistered
import com.deepfine.assignment.domain.usecase.auth.UseCaseLogin
import com.deepfine.assignment.domain.usecase.auth.validator.AuthValidator
import com.deepfine.assignment.feature.auth.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useCaseLogin: UseCaseLogin,
    private val useCaseIsEmailRegistered: UseCaseIsEmailRegistered
) : BaseViewModel<LoginContract.State, LoginContract.Event, LoginContract.Effect>() {

    init {
        launchWithInit { }
    }

    override fun setInitialState() = LoginContract.State()

    override fun handleEvents(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.OnEmailChanged -> onEmailChanged(event.text)
            is LoginContract.Event.OnPasswordChanged -> onPasswordChanged(event.text)
            LoginContract.Event.OnClickBottom -> onClickBottom()
        }
    }

    private fun onEmailChanged(text: String) {
        val prev = uiState.value
        val shouldReset =
            prev.emailValidity is LoginContract.EmailValidity.Invalid || prev.step != LoginContract.Step.Start

        setState {
            if (shouldReset) {
                copy(
                    email = text,

                    // 리셋
                    step = LoginContract.Step.Start,
                    password = "",
                    emailValidity = LoginContract.EmailValidity.Unknown
                )
            } else {
                copy(email = text)
            }
        }
    }

    private fun onPasswordChanged(text: String) {
        setState { copy(password = text) }
    }

    private fun onClickBottom() {
        when (uiState.value.step) {
            LoginContract.Step.Start -> handleStartBottom()
            LoginContract.Step.LoginPassword -> handleLoginBottom()
            LoginContract.Step.Signup -> handleSignupBottom()
        }
    }

    private fun handleStartBottom() {
        val email = uiState.value.email

        if (!AuthValidator.isValidEmail(email)) {
            setState { copy(emailValidity = LoginContract.EmailValidity.Invalid) }
            return
        }

        launchWithLoading {
            // valid -> DB 조회
            val registered = useCaseIsEmailRegistered(email)
            setState {
                copy(
                    step = if (registered) LoginContract.Step.LoginPassword else LoginContract.Step.Signup,
                    emailValidity = LoginContract.EmailValidity.Valid
                )
            }
        }
    }

    private fun handleLoginBottom() {
        val state = uiState.value
        launchWithLoading {
            val userInfo = useCaseLogin(state.email, state.password)
            setCommonEffect {
                CommonEffect.ShowToast(
                    message = UiText.StringResource(
                        if (userInfo != null)
                            R.string.login_toast_message_login_success
                        else
                            R.string.login_toast_message_login_fail
                    )
                )
            }
        }
    }

    private fun handleSignupBottom() {
        val email = uiState.value.email
        setEffect {
            LoginContract.Effect.Navigation.NavigateSignupScreen(email = email)
        }
    }
}