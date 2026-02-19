package com.deepfine.assignment.feature.auth.login.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.deepfine.assignment.core.common.util.UiText
import com.deepfine.assignment.core.feature.compose.theme.AppbarSection
import com.deepfine.assignment.core.feature.viewmodel.OverlayState
import com.deepfine.assignment.feature.auth.R
import com.deepfine.assignment.feature.auth.common.component.AuthActionButton
import com.deepfine.assignment.feature.auth.common.component.AuthUnderlineTextField
import com.deepfine.assignment.feature.auth.login.LoginContract
import com.deepfine.assignment.feature.auth.login.composable.part.emailFieldUi

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    uiState: LoginContract.State,
    overlayState: OverlayState,
    onEventSent: (event: LoginContract.Event) -> Unit
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val isLoading = overlayState is OverlayState.Loading

    LaunchedEffect(isLoading) {
        if (isLoading) focusManager.clearFocus()
    }

    Box(
        modifier = modifier
            .imePadding()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = AppbarSection.height, bottom = 54.dp)
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = uiState.step.topTitle.asString(context),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(10.dp))

            if (uiState.step.topMessage !is UiText.Empty) {
                Text(
                    text = uiState.step.topMessage.asString(context),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(40.dp))

            val emailUi = emailFieldUi(uiState)
            AuthUnderlineTextField(
                label = stringResource(id = R.string.login_text_field_email_label),
                hint = stringResource(id = R.string.login_text_field_email_hint),
                value = uiState.email,
                message = emailUi.messageRes?.let { stringResource(id = it) } ?: "",
                underlineColorOverride = emailUi.underlineColorOverride,
                onValueChange = { onEventSent(LoginContract.Event.OnEmailChanged(it)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onEventSent(LoginContract.Event.OnClickBottom) }
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (uiState.step == LoginContract.Step.LoginPassword) {
                AuthUnderlineTextField(
                    label = stringResource(id = R.string.login_text_field_password_label),
                    hint = stringResource(id = R.string.login_text_field_password_hint),
                    value = uiState.password,
                    onValueChange = { onEventSent(LoginContract.Event.OnPasswordChanged(it)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { onEventSent(LoginContract.Event.OnClickBottom) }
                    )
                )
            }
        }

        val bottomEnabled = when (uiState.step) {
            LoginContract.Step.Start -> uiState.email.isNotBlank()
            LoginContract.Step.LoginPassword -> uiState.password.isNotBlank()
            LoginContract.Step.Signup -> true
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(horizontal = 24.dp)
                .padding(top = 10.dp, bottom = 40.dp)
                .fillMaxWidth()
        ) {
            AuthActionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                enabled = bottomEnabled,
                loading = isLoading,
                onClick = { onEventSent(LoginContract.Event.OnClickBottom) },
                text = uiState.step.bottomButton.asString(context),
                textStyle = MaterialTheme.typography.bodyMedium
            )
        }
    }
}