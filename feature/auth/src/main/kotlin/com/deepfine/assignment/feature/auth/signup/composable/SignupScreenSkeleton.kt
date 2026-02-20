package com.deepfine.assignment.feature.auth.signup.composable

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
import com.deepfine.assignment.core.feature.compose.theme.AppbarSection
import com.deepfine.assignment.core.feature.compose.theme.BottomSection
import com.deepfine.assignment.core.feature.compose.theme.ContentSection
import com.deepfine.assignment.feature.auth.R
import com.deepfine.assignment.feature.auth.common.component.AuthActionButton
import com.deepfine.assignment.feature.auth.common.component.AuthUnderlineTextField
import com.deepfine.assignment.feature.auth.signup.composable.part.SignupAppbar

@Composable
fun SignupScreenSkeleton() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SignupAppbar(
            modifier = Modifier
                .fillMaxWidth()
                .height(AppbarSection.height),
            isBackButtonShow = false,
            progressOrder = 1,
            progressMax = 3,
            onBackButtonClick = {}
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = ContentSection.Padding.horizontal)
        ) {
            Spacer(modifier = Modifier.height(36.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.signup_top_title_name_step),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                modifier = Modifier.fillMaxWidth().height(64.dp),
                text = stringResource(R.string.signup_top_message_name_step),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            AuthUnderlineTextField(
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                label = stringResource(id = R.string.signup_text_field_name_label),
                hint = stringResource(id = R.string.signup_text_field_name_hint),
                value = "",
                onValueChange = {  }
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
                onClick = {  },
                text = ""
            )
        }
    }
}