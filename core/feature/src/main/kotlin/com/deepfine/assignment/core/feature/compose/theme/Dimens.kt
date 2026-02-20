package com.deepfine.assignment.core.feature.compose.theme

import androidx.compose.ui.unit.dp

object AppbarSection {
    val height = 73.dp // content 68.dp + progress 5.dp
}

object ContentSection {
    object Padding{
        val horizontal = 24.dp
    }
}

object BottomSection {
    val height = 99.dp // top 5.dp + button 54.dp + bottom 40.dp
    object Padding{
        val top = 5.dp
        val bottom = 40.dp
        val start = 24.dp
        val end = 24.dp
    }
}