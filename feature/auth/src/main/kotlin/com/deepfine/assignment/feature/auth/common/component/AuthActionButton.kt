package com.deepfine.assignment.feature.auth.common.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.deepfine.assignment.core.feature.compose.custom.modifier.clickSingle
import com.deepfine.assignment.core.feature.compose.theme.ColorSet

@Composable
fun AuthActionButton(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 12.dp,
    activeBackgroundColor: Color = MaterialTheme.colorScheme.primary,
    inactiveBackgroundColor: Color = ColorSet.gray_c2c3ca,

    enabled: Boolean = true,
    loading: Boolean = false,
    onClick: () -> Unit,

    text: String,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    inactiveTextColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    val backgroundColor = if (enabled) activeBackgroundColor else inactiveBackgroundColor
    val contentColor = if (enabled) textColor else inactiveTextColor

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(backgroundColor)
            .semantics(mergeDescendants = true) { role = Role.Button }
            .clickSingle(enabled = enabled && !loading) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (loading) {
            DualColorDonutSpinner()
        } else {
            Text(
                text = text,
                style = textStyle,
                color = contentColor
            )
        }
    }
}

@Composable
fun DualColorDonutSpinner(
    modifier: Modifier = Modifier,
    size: Dp = 22.dp,
    strokeWidth: Dp = 2.dp,
    color1: Color = MaterialTheme.colorScheme.onPrimary,
    color2: Color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f),
    durationMillis: Int = 900,
) {
    val transition = rememberInfiniteTransition(label = "dual_color_donut_spinner")
    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Canvas(
        modifier = modifier
            .size(size)
            .graphicsLayer { rotationZ = rotation }
    ) {
        val strokePx = strokeWidth.toPx()
        val pad = strokePx / 2f

        val arcSize = Size(
            width = this.size.width - pad * 2,
            height = this.size.height - pad * 2
        )
        val topLeft = Offset(pad, pad)

        drawArc(
            color = color1,
            startAngle = 0f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = Stroke(width = strokePx, cap = StrokeCap.Round)
        )

        drawArc(
            color = color2,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = Stroke(width = strokePx, cap = StrokeCap.Round)
        )
    }
}
