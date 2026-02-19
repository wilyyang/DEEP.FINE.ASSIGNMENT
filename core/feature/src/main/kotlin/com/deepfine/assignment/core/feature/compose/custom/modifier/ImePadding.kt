package com.deepfine.assignment.core.feature.compose.custom.modifier

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalDensity

fun Modifier.customImePadding(): Modifier = composed {
    val density = LocalDensity.current
    val ime = WindowInsets.ime.getBottom(density)
    val nav = WindowInsets.navigationBars.getBottom(density)
    val bottomPx = (ime - nav).coerceAtLeast(0)

    this.padding(bottom = with(density) { bottomPx.toDp() })
}