package com.deepfine.assignment.core.feature.compose.base.frame

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.deepfine.assignment.core.feature.compose.base.overlay.OverlayStateProcess
import com.deepfine.assignment.core.feature.viewmodel.OverlayState
import kotlinx.coroutines.delay

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,

    statusBarColor: Color? = null,
    isStatusBarTextDark: Boolean = true,

    // overlay state
    overlayState: OverlayState,
    initContent: (@Composable () -> Unit)? = null,

    content: @Composable () -> Unit
) {
    val statusBarPaddingDp =
        with(LocalDensity.current) { WindowInsets.statusBars.getTop(this).toDp() }
    val navigationBarPaddingDp =
        with(LocalDensity.current) { WindowInsets.navigationBars.getBottom(this).toDp() }

    val view = LocalView.current
    SideEffect {
        (view.context as Activity).window.let {
            WindowCompat.getInsetsController(it, view).isAppearanceLightStatusBars =
                isStatusBarTextDark
        }
    }

    // Init 상태 화면 처리 : ScreenAnimationDelay > InitShow > InitFadingOut > None
    val initShowState =
        remember { mutableStateOf(if (overlayState is OverlayState.Init) InitShowState.ScreenAnimationDelay else InitShowState.None) }

    LaunchedEffect(initShowState.value) {
        when (initShowState.value) {
            InitShowState.ScreenAnimationDelay -> {
                delay(DELAY_SCREEN_ANIMATION_MS)
                initShowState.value = InitShowState.InitShow
            }

            InitShowState.InitShow -> {
                initShowState.value = initShowState.value.transState(overlayState)
            }

            InitShowState.InitFadingOut -> {
                delay(DELAY_INIT_SCREEN_FADE_OUT_MS)
                initShowState.value = InitShowState.None
            }

            else -> {}
        }
    }

    LaunchedEffect(overlayState) {
        initShowState.value = initShowState.value.transState(overlayState)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = navigationBarPaddingDp)
    ) {
        // Status Bar Color
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(if (statusBarColor != null && statusBarColor != Color.Transparent) statusBarPaddingDp else 0.dp)
                .background(color = statusBarColor ?: Color.Transparent)
        )

        BaseContent(
            modifier = modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.surface),
            initShowState = initShowState.value,
            initContent = initContent,
            content = content
        )
    }

    if (initShowState.value == InitShowState.None) {
        OverlayStateProcess(
            state = overlayState
        )
    }
}