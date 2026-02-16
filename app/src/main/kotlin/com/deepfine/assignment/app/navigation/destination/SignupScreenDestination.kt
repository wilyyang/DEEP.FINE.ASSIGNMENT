package com.deepfine.assignment.app.navigation.destination

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.deepfine.assignment.app.navigation.HandleCommonEffect
import com.deepfine.assignment.core.feature.viewmodel.CommonEvent
import com.deepfine.assignment.feature.auth.signup.SignupContract
import com.deepfine.assignment.feature.auth.signup.SignupViewModel
import com.deepfine.assignment.feature.auth.signup.composable.SignupScreenFrame

@Composable
fun SignupScreenDestination(
    navController: NavController
) {
    val viewModel: SignupViewModel = hiltViewModel()

    val onEventSent = remember {
        { event: SignupContract.Event ->
            viewModel.setEvent(event)
        }
    }

    val onCommonEventSent = remember {
        { event: CommonEvent ->
            viewModel.setCommonEvent(event)
        }
    }

    val onEffectRequested: (SignupContract.Effect) -> Unit = remember {
        { effect ->
            handleEffectRequest(effect, navController)
        }
    }

    HandleCommonEffect(
        navController = navController,
        commonEffectFlow = viewModel.commonEffect,
        onCommonEventSent = onCommonEventSent
    )

    SignupScreenFrame(
        uiState = viewModel.uiState.value,
        overlayState = viewModel.overlayState.value,
        onEventSent = onEventSent,
        onCommonEventSent = onCommonEventSent,
        effectFlow = viewModel.effect,
        onEffectRequested = onEffectRequested
    )
}

fun handleEffectRequest(effect: SignupContract.Effect, navController: NavController) {
    // TODO
}