package com.deepfine.assignment.feature.auth.login.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.deepfine.assignment.core.feature.compose.custom.modifier.clickSingle
import com.deepfine.assignment.feature.auth.login.LoginContract

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    uiState: LoginContract.State,
    onEventSent: (event: LoginContract.Event) -> Unit
) {
    Box(modifier = modifier) {
        Text(text = "로그인 1", modifier = Modifier.align(Alignment.TopStart).clickSingle{
            onEventSent(LoginContract.Event.OnClickSignup)
        })
        Text(text = "로그인 2", modifier = Modifier.align(Alignment.BottomEnd))
    }
}