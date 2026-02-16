package com.deepfine.assignment.feature.auth.login

import androidx.lifecycle.SavedStateHandle
import com.deepfine.assignment.core.feature.viewmodel.BaseViewModel
import com.deepfine.assignment.domain.usecase.auth.UseCaseIsEmailRegistered
import com.deepfine.assignment.domain.usecase.auth.UseCaseLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useCaseLogin: UseCaseLogin,
    private val useCaseIsEmailRegistered: UseCaseIsEmailRegistered
) : BaseViewModel<LoginContract.State, LoginContract.Event, LoginContract.Effect>() {

    init {
        launchWithInit {

        }
    }

    override fun setInitialState() = LoginContract.State()

    override fun handleEvents(event: LoginContract.Event) {
        when (event) {
            LoginContract.Event.OnClickSignup -> handleOnClickSignup("테스트 이메일")
        }
    }

    private fun handleOnClickSignup(email: String) {
        setEffect {
            LoginContract.Effect.Navigation.NavigateSignupScreen(email = email)
        }
    }
}