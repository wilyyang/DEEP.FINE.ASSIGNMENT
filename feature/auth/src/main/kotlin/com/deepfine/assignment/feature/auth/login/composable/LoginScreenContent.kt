package com.deepfine.assignment.feature.auth.login.composable

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.deepfine.assignment.core.common.util.UiText
import com.deepfine.assignment.core.feature.compose.custom.component.UnderlineTextField
import com.deepfine.assignment.core.feature.compose.theme.TopSection
import com.deepfine.assignment.feature.auth.R
import com.deepfine.assignment.feature.auth.login.LoginContract

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    uiState: LoginContract.State,
    onEventSent: (event: LoginContract.Event) -> Unit
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
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
                .padding(top = TopSection.height, bottom = 54.dp)
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

            // email_under_message
            val emailUnderMessageRes: Int? = when (uiState.step) {
                LoginContract.Step.Start -> {
                    if (uiState.emailValidity is LoginContract.EmailValidity.Invalid) {
                        R.string.login_text_field_email_message_invalid
                    } else null
                }

                LoginContract.Step.LoginPassword -> R.string.login_text_field_email_message_login_step
                LoginContract.Step.Signup -> R.string.login_text_field_email_message_signup_step
            }

            // under_line_color
            val emailUnderlineColor = when (uiState.step) {
                LoginContract.Step.Start -> when {
                    // 최초 화면
                    !uiState.emailTouched -> MaterialTheme.colorScheme.outline
                    // email invalid
                    uiState.emailValidity is LoginContract.EmailValidity.Invalid -> MaterialTheme.colorScheme.error
                    // 텍스트 없음 and 커서 없음
                    uiState.email.isBlank() && !uiState.isEmailCursor -> MaterialTheme.colorScheme.error
                    // 텍스트 있음 or 커서 있음
                    else -> MaterialTheme.colorScheme.primary
                }

                LoginContract.Step.LoginPassword,
                LoginContract.Step.Signup -> MaterialTheme.colorScheme.primary
            }

            UnderlineTextField(
                label = stringResource(id = R.string.login_text_field_email_label),
                hint = stringResource(id = R.string.login_text_field_email_hint),
                value = uiState.email,
                message = emailUnderMessageRes?.let { stringResource(id = it) } ?: "",
                underlineColor = emailUnderlineColor,
                onFocusChanged = { onEventSent(LoginContract.Event.OnEmailCursorChanged(it)) },
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
                UnderlineTextField(
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
                .padding(horizontal = 24.dp, vertical = 40.dp)
                .clip(shape = RoundedCornerShape(12.dp))
                .fillMaxWidth()
        ) {
            Button(
                enabled = bottomEnabled,
                onClick = { onEventSent(LoginContract.Event.OnClickBottom) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            ) {
                Text(
                    text = uiState.step.bottomButton.asString(context),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}