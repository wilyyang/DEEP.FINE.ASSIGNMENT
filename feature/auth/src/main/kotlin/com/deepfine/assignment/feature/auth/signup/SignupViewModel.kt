package com.deepfine.assignment.feature.auth.signup

import androidx.lifecycle.SavedStateHandle
import com.deepfine.assignment.core.common.app.ArgName.NAME_USER_EMAIL
import com.deepfine.assignment.core.common.util.UiText
import com.deepfine.assignment.core.feature.viewmodel.BaseViewModel
import com.deepfine.assignment.core.feature.viewmodel.CommonEffect
import com.deepfine.assignment.core.feature.viewmodel.CommonEvent
import com.deepfine.assignment.core.feature.viewmodel.OverlayState
import com.deepfine.assignment.domain.usecase.auth.UseCaseRegisterUser
import com.deepfine.assignment.domain.usecase.auth.exception.RegisterException
import com.deepfine.assignment.domain.usecase.auth.validator.AuthValidator
import com.deepfine.assignment.feature.auth.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useCaseRegisterUser: UseCaseRegisterUser
) : BaseViewModel<SignupContract.State, SignupContract.Event, SignupContract.Effect>() {

    private val email: String by lazy { requireNotNull(savedStateHandle.get<String>(NAME_USER_EMAIL)) }

    init {
        launchWithInit {  }
    }

    override fun setInitialState() = SignupContract.State()

    override fun handleEvents(event: SignupContract.Event) {
        when (event) {
            is SignupContract.Event.OnNameChanged -> onNameChanged(event.text)
            is SignupContract.Event.OnPasswordChanged -> onPasswordChanged(event.text)
            SignupContract.Event.OnClickBottom -> onClickBottom()
        }
    }

    private fun onNameChanged(text: String) {
        setState { copy(name = text) }
    }

    private fun onPasswordChanged(text: String) {
        setState { copy(password = text) }
    }

    private fun onClickBottom() {
        when (uiState.value.step) {
            SignupContract.Step.Name -> handleNameBottom()
            SignupContract.Step.Password -> handlePasswordBottom()
            is SignupContract.Step.Complete -> handleCompleteBottom()
        }
    }

    private fun handleNameBottom() {
        setState { copy(step = SignupContract.Step.Password) }
    }

    private fun handlePasswordBottom() {
        val password = uiState.value.password

        if (!AuthValidator.isValidPassword(password)) {
            setCommonEffect {
                CommonEffect.ShowToast(message = UiText.StringResource(R.string.signup_toast_message_invalid_password))
            }
            return
        }

        launchWithLoading {
            useCaseRegisterUser(email = email, name = uiState.value.name, password = password)
            setState {
                copy(
                    step = SignupContract.Step.Complete
                )
            }
        }
    }

    private fun handleCompleteBottom() {
        setEffect {
            SignupContract.Effect.Navigation.NavigateLoginScreen
        }
    }

    override fun handleCommonEvents(event: CommonEvent) {
        when (event) {
            is CommonEvent.CloseEvent -> {
                when (uiState.value.step) {
                    SignupContract.Step.Name -> super.handleCommonEvents(CommonEvent.CloseEvent)
                    SignupContract.Step.Password -> {
                        setState {
                            copy(
                                step = SignupContract.Step.Name,
                                password = ""
                            )
                        }
                    }

                    is SignupContract.Step.Complete -> {
                        setEffect {
                            SignupContract.Effect.Navigation.NavigateLoginScreen
                        }
                    }
                }
            }

            else -> super.handleCommonEvents(event)
        }
    }

    override fun showExceptionResult(
        throwable: Throwable,
        defaultConfirm: () -> Unit,
        defaultDismiss: () -> Unit
    ) {
        if (throwable is RegisterException) {
            val title = UiText.StringResource(R.string.signup_error_title)
            val message = when (throwable) {
                is RegisterException.DuplicateEmail -> UiText.StringResource(R.string.signup_error_duplicate_email)
                is RegisterException.StorageError -> UiText.StringResource(R.string.signup_dialog_error_default)
            }

            val confirm: () -> Unit = {
                setEffect {
                    SignupContract.Effect.Navigation.NavigateLoginScreen
                }
            }

            setOverlayState(
                OverlayState.CommonDialog(
                    title = title,
                    message = message,
                    dismissText = null,
                    onConfirm = confirm
                )
            )
            return
        }

        super.showExceptionResult(throwable, defaultConfirm, defaultDismiss)
    }
}