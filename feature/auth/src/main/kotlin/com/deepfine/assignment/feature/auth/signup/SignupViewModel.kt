package com.deepfine.assignment.feature.auth.signup

import androidx.lifecycle.SavedStateHandle
import com.deepfine.assignment.core.common.app.ArgName.NAME_USER_EMAIL
import com.deepfine.assignment.core.feature.viewmodel.BaseViewModel
import com.deepfine.assignment.domain.usecase.auth.UseCaseRegisterUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useCaseRegisterUser: UseCaseRegisterUser
) : BaseViewModel<SignupContract.State, SignupContract.Event, SignupContract.Effect>() {
    init {
        launchWithInit {
            val email = requireNotNull(savedStateHandle.get<String>(NAME_USER_EMAIL))
            setState {
                copy(
                    email = email
                )
            }
        }
    }

    override fun setInitialState() = SignupContract.State()

    override fun handleEvents(event: SignupContract.Event) {
        when (event) {
            else -> {}
        }
    }
}