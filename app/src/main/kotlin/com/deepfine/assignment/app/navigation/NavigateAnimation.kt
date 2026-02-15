package com.deepfine.assignment.app.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val ANIM_DURATION_HORIZONTAL_ENTER = 300
const val ANIM_DURATION_HORIZONTAL_EXIT = 300

typealias EnterLambda = AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?
typealias ExitLambda = AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?

/**
 * Horizontal Animations
 */
val slideLeftEnter: EnterLambda = {
    slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(ANIM_DURATION_HORIZONTAL_ENTER),
    )
}

val slideRightExit: ExitLambda = {
    slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(ANIM_DURATION_HORIZONTAL_EXIT),
    )
}

object NavigateAnimation {
    fun NavGraphBuilder.leftScreenTransition(
        route: String,
        arguments: List<NamedNavArgument> = emptyList(),
        deepLinks: List<NavDeepLink> = emptyList(),
        content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
    ) = composable(
        route = route,
        arguments = arguments,
        deepLinks = deepLinks,
        enterTransition = slideLeftEnter,
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = slideRightExit,
        content = content
    )
}