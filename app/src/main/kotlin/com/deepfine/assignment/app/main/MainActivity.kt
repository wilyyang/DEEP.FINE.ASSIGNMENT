package com.deepfine.assignment.app.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.deepfine.assignment.app.navigation.AppScreenConfiguration
import com.deepfine.assignment.core.feature.compose.theme.DeepfineTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DeepfineTheme {
                AppScreenConfiguration()
            }
        }
    }
}