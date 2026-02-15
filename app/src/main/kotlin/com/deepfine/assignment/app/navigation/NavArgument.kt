package com.deepfine.assignment.app.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.deepfine.assignment.core.common.app.ArgName

object NavArgument {
    val argUserEmail = navArgument(ArgName.NAME_USER_EMAIL) {
        type = NavType.StringType
        defaultValue = ""
    }
}