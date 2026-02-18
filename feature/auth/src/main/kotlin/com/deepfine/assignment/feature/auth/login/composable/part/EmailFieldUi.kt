package com.deepfine.assignment.feature.auth.login.composable.part

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.deepfine.assignment.feature.auth.R
import com.deepfine.assignment.feature.auth.login.LoginContract

data class EmailFieldUi(
    val messageRes: Int?,
    val underlineColorOverride: Color?
)

@Composable
fun emailFieldUi(uiState: LoginContract.State): EmailFieldUi {
    return when (uiState.step) {
        LoginContract.Step.Start -> {
            val invalid = uiState.emailValidity is LoginContract.EmailValidity.Invalid
            EmailFieldUi(
                messageRes = if (invalid) R.string.login_text_field_email_message_invalid else null,
                underlineColorOverride = if (invalid) MaterialTheme.colorScheme.error else null
            )
        }

        LoginContract.Step.LoginPassword ->
            EmailFieldUi(
                messageRes = R.string.login_text_field_email_message_login_step,
                underlineColorOverride = MaterialTheme.colorScheme.primary
            )

        LoginContract.Step.Signup ->
            EmailFieldUi(
                messageRes = R.string.login_text_field_email_message_signup_step,
                underlineColorOverride = MaterialTheme.colorScheme.primary
            )
    }
}
