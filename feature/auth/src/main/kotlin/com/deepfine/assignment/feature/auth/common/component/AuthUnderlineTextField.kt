package com.deepfine.assignment.feature.auth.common.component

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.deepfine.assignment.core.feature.compose.custom.component.UnderlineTextField

@Composable
fun AuthUnderlineTextField(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,

    label: String = "",
    value: String,
    onValueChange: (String) -> Unit,

    hint: String = "",
    underlineColorOverride: Color? = null,
    message: String = "",

    labelStyle: TextStyle = MaterialTheme.typography.labelLarge,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    messageStyle: TextStyle = MaterialTheme.typography.labelMedium,

    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    var touched by rememberSaveable { mutableStateOf(false) }
    var focused by remember { mutableStateOf(false) }

    val underlineColor = underlineColorOverride ?: run {
        when {
            // 최초
            !touched -> MaterialTheme.colorScheme.outline
            // 커서 없음 + 텍스트 없음
            !focused && value.isBlank() -> MaterialTheme.colorScheme.error
            // 나머지
            else -> MaterialTheme.colorScheme.primary
        }
    }

    UnderlineTextField(
        modifier = modifier,
        enabled = enabled,

        label = label,
        value = value,
        onValueChange = onValueChange,

        hint = hint,
        underlineColor = underlineColor,
        message = message,
        messageColor = underlineColor,

        onFocusChanged = { hasFocus ->
            focused = hasFocus
            if (hasFocus) touched = true
        },

        labelStyle = labelStyle,
        textStyle = textStyle,
        messageStyle = messageStyle,

        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}