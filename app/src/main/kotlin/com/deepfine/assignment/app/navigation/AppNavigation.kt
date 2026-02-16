package com.deepfine.assignment.app.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.deepfine.assignment.app.navigation.NavigateAnimation.leftScreenTransition
import com.deepfine.assignment.app.navigation.destination.LoginScreenDestination
import com.deepfine.assignment.app.navigation.destination.SignupScreenDestination
import com.deepfine.assignment.core.common.app.ArgName
import com.deepfine.assignment.feature.auth.login.LoginContract
import com.deepfine.assignment.feature.auth.signup.SignupContract

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppScreenConfiguration() {
    CompositionLocalProvider(
        LocalOverscrollFactory provides null
    ){
        AppNavigation()
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LoginContract.NAME
    ) {
        leftScreenTransition(
            route = LoginContract.NAME
        ) {
            LoginScreenDestination(navController = navController)
        }

        leftScreenTransition(
            route = "${SignupContract.NAME}/{${ArgName.NAME_USER_EMAIL}}",
            arguments = listOf(NavArgument.argUserEmail)

        ) {
            SignupScreenDestination(navController = navController)
        }
    }
}