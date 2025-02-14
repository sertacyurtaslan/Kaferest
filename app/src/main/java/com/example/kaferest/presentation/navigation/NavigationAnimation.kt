package com.example.kaferest.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.navigation.NavBackStackEntry

object NavigationAnimation {
    private const val ANIMATION_DURATION = 500

    fun AnimatedContentTransitionScope<NavBackStackEntry>.enterTransition(): EnterTransition =
        slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
            animationSpec = tween(
                durationMillis = ANIMATION_DURATION,
                easing = FastOutSlowInEasing
            )
        )

    fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransition(): ExitTransition =
        slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Left,
            animationSpec = tween(
                durationMillis = ANIMATION_DURATION,
                easing = FastOutSlowInEasing
            )
        )

    fun AnimatedContentTransitionScope<NavBackStackEntry>.popEnterTransition(): EnterTransition =
        slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Right,
            animationSpec = tween(
                durationMillis = ANIMATION_DURATION,
                easing = FastOutSlowInEasing
            )
        )

    fun AnimatedContentTransitionScope<NavBackStackEntry>.popExitTransition(): ExitTransition =
        slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Right,
            animationSpec = tween(
                durationMillis = ANIMATION_DURATION,
                easing = FastOutSlowInEasing
            )
        )
} 