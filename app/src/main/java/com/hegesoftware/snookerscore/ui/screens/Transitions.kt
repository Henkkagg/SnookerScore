package com.hegesoftware.snookerscore.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.TweenSpec
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.spec.DestinationStyle

@OptIn(ExperimentalAnimationApi::class)
object GameRootTransitions : DestinationStyle.Animated {

    override fun AnimatedContentScope<NavBackStackEntry>.enterTransition(): EnterTransition {
        return slideInHorizontally(
            animationSpec = TweenSpec(),
            initialOffsetX = { it }
        )
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popExitTransition(): ExitTransition {
        return slideOutHorizontally(
            animationSpec = TweenSpec(),
            targetOffsetX = { it }
        )
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popEnterTransition(): EnterTransition? {
        return slideInVertically(
            animationSpec = TweenSpec(),
            initialOffsetY = { -it }
        )
    }

    override fun AnimatedContentScope<NavBackStackEntry>.exitTransition(): ExitTransition? {
        return slideOutVertically(
            animationSpec = TweenSpec(),
            targetOffsetY = { -it }
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
object LicensesTransitions : DestinationStyle.Animated {
    override fun AnimatedContentScope<NavBackStackEntry>.enterTransition(): EnterTransition {
        return slideInHorizontally(
            animationSpec = TweenSpec(),
            initialOffsetX = { it }
        )
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popExitTransition(): ExitTransition {
        return slideOutHorizontally(
            animationSpec = TweenSpec(),
            targetOffsetX = { it }
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
object NewGameTransitions : DestinationStyle.Animated {
    override fun AnimatedContentScope<NavBackStackEntry>.enterTransition(): EnterTransition {
        return slideInHorizontally(
            animationSpec = TweenSpec(),
            initialOffsetX = { -it }
        )
    }

    override fun AnimatedContentScope<NavBackStackEntry>.exitTransition(): ExitTransition {
        return slideOutHorizontally(
            animationSpec = TweenSpec(),
            targetOffsetX = { -it }
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
object SettingsTransitions : DestinationStyle.Animated {
    override fun AnimatedContentScope<NavBackStackEntry>.enterTransition(): EnterTransition? {
        return slideInVertically(
            animationSpec = TweenSpec(),
            initialOffsetY = { it }
        )
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popExitTransition(): ExitTransition? {
        return slideOutVertically(
            animationSpec = TweenSpec(),
            targetOffsetY = { it }
        )
    }
}