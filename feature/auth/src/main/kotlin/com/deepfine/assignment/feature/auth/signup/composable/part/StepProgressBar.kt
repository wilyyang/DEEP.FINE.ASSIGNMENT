package com.deepfine.assignment.feature.auth.signup.composable.part

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.deepfine.assignment.core.feature.compose.theme.ColorSet

@Composable
fun StepProgressBar(
    step: Int,
    max: Int,
    modifier: Modifier = Modifier,
    radius: Dp = 2.5.dp,
    backgroundColor: Color = ColorSet.gray_eeeeee,
    progressColor: Color = MaterialTheme.colorScheme.primary,
    animDurationMs: Int = 450,
) {
    val fraction = (step.coerceIn(0, max) / max.toFloat())

    val animatedFraction by animateFloatAsState(
        targetValue = fraction,
        animationSpec = tween(durationMillis = animDurationMs, easing = FastOutSlowInEasing),
        label = "step_progress"
    )

    Box(modifier = modifier.background(backgroundColor)) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(animatedFraction)
                .clip(
                    if (animatedFraction < 1f) {
                        RoundedCornerShape(
                            topStart = 0.dp,
                            bottomStart = 0.dp,
                            topEnd = radius,
                            bottomEnd = radius
                        )
                    } else {
                        RoundedCornerShape(0.dp)
                    }
                )
                .background(progressColor)
        )
    }
}