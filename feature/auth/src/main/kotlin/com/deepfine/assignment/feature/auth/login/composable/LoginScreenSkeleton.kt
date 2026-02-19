package com.deepfine.assignment.feature.auth.login.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.deepfine.assignment.core.feature.compose.theme.BottomSection
import com.deepfine.assignment.feature.auth.R
import com.deepfine.assignment.feature.auth.common.component.AuthActionButton
import com.deepfine.assignment.feature.auth.common.component.AuthUnderlineTextField

@Composable
fun LoginScreenSkeleton() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(116.dp))

            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = stringResource(R.string.login_top_title_start_step),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
            )

            Text(
                modifier = Modifier.padding(bottom = 10.dp).height(54.dp),
                text = stringResource(R.string.login_top_message_start_step),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
            )

            AuthUnderlineTextField(
                enabled = false,
                label = stringResource(id = R.string.login_text_field_email_label),
                hint = stringResource(id = R.string.login_text_field_email_hint),
                value = "",
                onValueChange = { }
            )
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
                enabled = false,
                loading = true,
                onClick = { },
                text = "",
                textStyle = MaterialTheme.typography.bodyMedium
            )
        }
    }
}