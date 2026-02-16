package com.deepfine.assignment.feature.auth.signup.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.deepfine.assignment.feature.auth.signup.SignupContract

@Composable
fun SignupScreenContent(
    modifier: Modifier = Modifier,
    uiState: SignupContract.State,
    onEventSent: (event: SignupContract.Event) -> Unit
) {
    Box(modifier = modifier) {
        Text(text = "회원가입 1 : ${uiState.email}", modifier = Modifier.align(Alignment.TopStart))
        Text(text = "회원가입 2", modifier = Modifier.align(Alignment.BottomEnd))
    }
}