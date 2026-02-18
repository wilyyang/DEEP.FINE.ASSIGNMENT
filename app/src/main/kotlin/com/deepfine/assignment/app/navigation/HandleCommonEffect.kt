package com.deepfine.assignment.app.navigation

import android.app.Activity
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.deepfine.assignment.core.feature.viewmodel.CommonEffect
import com.deepfine.assignment.core.feature.viewmodel.CommonEvent
import kotlinx.coroutines.flow.Flow

@Composable
fun HandleCommonEffect(
    navController: NavController,
    commonEffectFlow: Flow<CommonEffect>,
    onCommonEventSent: (event: CommonEvent) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(commonEffectFlow) {
        commonEffectFlow.collect { effect ->
            when (effect) {
                is CommonEffect.ShowToast -> {
                    Toast.makeText(context, effect.message.asString(context), effect.duration)
                        .show()
                }
                is CommonEffect.Navigation.NavigateBack -> {
                    val popped = navController.popBackStack()
                    if (!popped) {
                        (navController.context as? Activity)?.finish()
                    }
                }
            }
        }
    }
}