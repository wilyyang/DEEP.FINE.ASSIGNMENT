package com.deepfine.assignment.feature.auth.signup.composable.part

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.deepfine.assignment.core.feature.compose.custom.modifier.clickSingle
import com.deepfine.assignment.feature.auth.R

@Composable
fun SignupAppbar(
    modifier: Modifier = Modifier,
    isBackButtonShow: Boolean,
    progressOrder: Int,
    progressMax: Int,
    onBackButtonClick: () -> Unit
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 7.dp)
                .padding(horizontal = 16.dp)
        ) {
            if (isBackButtonShow) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(48.dp)
                        .clickSingle { onBackButtonClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(id = R.drawable.ic_arrow_left),
                        contentDescription = "Back",
                        contentScale = ContentScale.Fit,
                    )
                }
            }
        }

        StepProgressBar(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .height(5.dp),
            step = progressOrder,
            max = progressMax
        )
    }
}