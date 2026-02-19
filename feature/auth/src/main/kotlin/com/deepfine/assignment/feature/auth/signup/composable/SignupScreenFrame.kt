package com.deepfine.assignment.feature.auth.signup.composable

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.deepfine.assignment.core.feature.compose.base.frame.BaseScreen
import com.deepfine.assignment.core.feature.viewmodel.CommonEvent
import com.deepfine.assignment.core.feature.viewmodel.OverlayState
import com.deepfine.assignment.feature.auth.signup.SignupContract
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun SignupScreenFrame(
    uiState: SignupContract.State,
    overlayState: OverlayState,
    onEventSent: (event: SignupContract.Event) -> Unit,
    onCommonEventSent: (event: CommonEvent) -> Unit,
    effectFlow: SharedFlow<SignupContract.Effect>?,
    onEffectRequested: (SignupContract.Effect) -> Unit
) {
    LaunchedEffect(Unit) {
        effectFlow?.onEach { effect ->
            onEffectRequested(effect)
        }?.collect()
    }

    BaseScreen(
        overlayState = overlayState,
        statusBarColor = MaterialTheme.colorScheme.surface,
        showDefaultLoadingIndicator = false,
        initContent = {
            SignupScreenSkeleton()
        }
    ) {
        SignupScreenContent(
            modifier = Modifier.fillMaxSize(),
            uiState = uiState,
            overlayState = overlayState,
            onEventSent = onEventSent,
            onCommonEventSent = onCommonEventSent
        )
    }

    BackHandler {
        onCommonEventSent(CommonEvent.CloseEvent)
    }
}