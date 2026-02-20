package com.deepfine.assignment.feature.auth.signup.composable

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.deepfine.assignment.core.feature.compose.custom.modifier.customImePadding
import com.deepfine.assignment.core.feature.compose.theme.AppbarSection
import com.deepfine.assignment.core.feature.compose.theme.BottomSection
import com.deepfine.assignment.core.feature.compose.theme.ContentSection
import com.deepfine.assignment.core.feature.viewmodel.CommonEvent
import com.deepfine.assignment.core.feature.viewmodel.OverlayState
import com.deepfine.assignment.feature.auth.R
import com.deepfine.assignment.feature.auth.common.component.AuthActionButton
import com.deepfine.assignment.feature.auth.common.component.AuthUnderlineTextField
import com.deepfine.assignment.feature.auth.signup.SignupContract
import com.deepfine.assignment.feature.auth.signup.composable.part.SignupAppbar

@Composable
fun SignupScreenContent(
    modifier: Modifier = Modifier,
    uiState: SignupContract.State,
    overlayState: OverlayState,
    onEventSent: (event: SignupContract.Event) -> Unit,
    onCommonEventSent: (event: CommonEvent) -> Unit
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val isLoading = overlayState is OverlayState.Loading

    val onClickBottom = {
        focusManager.clearFocus()
        onEventSent(SignupContract.Event.OnClickBottom)
    }

    Column(
        modifier = modifier
            .customImePadding()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        SignupAppbar(
            modifier = Modifier
                .fillMaxWidth()
                .height(AppbarSection.height),
            isBackButtonShow = uiState.step !is SignupContract.Step.Complete,
            progressOrder = uiState.step.order,
            progressMax = 3,
            onBackButtonClick = {
                focusManager.clearFocus()
                onCommonEventSent(CommonEvent.CloseEvent)
            }
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = ContentSection.Padding.horizontal)
        ) {
            item {
                Spacer(modifier = Modifier.height(36.dp))
            }

            item {
                Column {
                    if(uiState.step is SignupContract.Step.Complete){
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(R.string.signup_top_title_complete_name, uiState.name),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = uiState.step.topTitle.asString(context),
                        style = MaterialTheme.typography.titleSmall
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        modifier = Modifier.fillMaxWidth().height(64.dp),
                        text = uiState.step.topMessage.asString(context),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            item {
                when (uiState.step) {
                    SignupContract.Step.Name -> {
                        AuthUnderlineTextField(
                            modifier = Modifier.fillMaxWidth(),
                            label = stringResource(id = R.string.signup_text_field_name_label),
                            hint = stringResource(id = R.string.signup_text_field_name_hint),
                            value = uiState.name,
                            onValueChange = { onEventSent(SignupContract.Event.OnNameChanged(it)) },
                            keyboardActions = KeyboardActions(
                                onDone = { onClickBottom() }
                            )
                        )
                    }

                    SignupContract.Step.Password -> {
                        AuthUnderlineTextField(
                            modifier = Modifier.fillMaxWidth(),
                            label = stringResource(id = R.string.signup_text_field_password_label),
                            hint = stringResource(id = R.string.signup_text_field_password_hint),
                            value = uiState.password,
                            onValueChange = { onEventSent(SignupContract.Event.OnPasswordChanged(it)) },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = { onClickBottom() }
                            )
                        )
                    }

                    else -> {}
                }
            }
        }

        val bottomEnabled = when (uiState.step) {
            SignupContract.Step.Name -> uiState.name.isNotBlank()
            SignupContract.Step.Password -> uiState.password.isNotBlank()
            is SignupContract.Step.Complete -> true
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
                onClick = { onClickBottom() },
                text = uiState.step.bottomButton.asString(context),
                textStyle = MaterialTheme.typography.bodyMedium
            )
        }
    }
}