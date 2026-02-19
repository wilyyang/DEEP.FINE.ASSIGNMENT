package com.deepfine.assignment.feature.auth.login.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.deepfine.assignment.core.common.util.UiText
import com.deepfine.assignment.core.feature.compose.custom.modifier.customImePadding
import com.deepfine.assignment.core.feature.compose.theme.BottomSection
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

    Column(
        modifier = modifier
            .customImePadding()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 24.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(116.dp))
            }

            item {
                Column {
                    Text(
                        modifier = Modifier.padding(bottom = 10.dp),
                        text = uiState.step.topTitle.asString(context),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Box(
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .height(54.dp)
                    ) {

                        if (uiState.step.topMessage !is UiText.Empty) {
                            Text(
                                text = uiState.step.topMessage.asString(context),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            item {
                Column {
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

                    if (uiState.step == LoginContract.Step.LoginPassword) {

                        Spacer(modifier = Modifier.height(24.dp))

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

                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }

        val bottomEnabled = when (uiState.step) {
            LoginContract.Step.Start -> uiState.email.isNotBlank()
            LoginContract.Step.LoginPassword -> uiState.password.isNotBlank()
            LoginContract.Step.Signup -> true
        }

        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
                .fillMaxWidth()
                .height(BottomSection.height)
                .padding(
                    top = BottomSection.Padding.top,
                    bottom = BottomSection.Padding.bottom,
                    start = BottomSection.Padding.start,
                    end = BottomSection.Padding.end
                )
        ) {
            AuthActionButton(
                modifier = Modifier.fillMaxSize(),
                enabled = bottomEnabled,
                loading = isLoading,
                onClick = { onEventSent(LoginContract.Event.OnClickBottom) },
                text = uiState.step.bottomButton.asString(context),
                textStyle = MaterialTheme.typography.bodyMedium
            )
        }
    }
}