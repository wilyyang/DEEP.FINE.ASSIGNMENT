package com.deepfine.assignment.core.feature.compose.custom.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.deepfine.assignment.core.feature.R
import com.deepfine.assignment.core.feature.compose.custom.modifier.clickSingle

@Composable
fun UnderlineTextField(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,

    label: String = "",
    value: String,
    onValueChange: (String) -> Unit,

    hint: String = "",
    underlineColor: Color = MaterialTheme.colorScheme.outline,
    message: String = "",
    messageColor: Color = underlineColor,

    onFocusChanged: (Boolean) -> Unit = {},

    labelStyle: TextStyle = MaterialTheme.typography.labelLarge,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    messageStyle: TextStyle = MaterialTheme.typography.labelMedium,

    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    val isPassword =
        keyboardOptions.keyboardType == KeyboardType.Password || keyboardOptions.keyboardType == KeyboardType.NumberPassword

    val visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None

    Column(modifier = modifier) {

        if (label.isNotBlank()) {
            Text(
                text = label,
                style = labelStyle,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 10.dp)
                    .onFocusChanged { onFocusChanged(it.isFocused) },
                enabled = enabled,
                value = value,
                onValueChange = onValueChange,
                textStyle = textStyle.copy(color = MaterialTheme.colorScheme.onSurface),
                visualTransformation = visualTransformation,
                singleLine = true,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                decorationBox = { innerTextField ->
                    if (value.isEmpty() && hint.isNotBlank()) {
                        Text(
                            text = hint,
                            style = textStyle,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    innerTextField()
                }
            )

            if (enabled && !isPassword && value.isNotEmpty()) {
                Image(
                    painter = painterResource(id = R.drawable.ic_cancel),
                    contentDescription = "Cancel",
                    modifier = Modifier
                        .padding(start = 4.dp, end = 1.dp)
                        .size(16.dp)
                        .clickSingle { onValueChange("") },
                    contentScale = ContentScale.Fit,
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = underlineColor
        )

        if (message.isNotBlank()) {
            Spacer(Modifier.height(5.dp))
            Text(
                text = message,
                style = messageStyle,
                color = messageColor
            )
        }
    }
}
