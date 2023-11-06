package io.chipmango.navigation.navigator

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import io.chipmango.navigation.destination.Destination

@Composable
fun Navigator(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startingDestination: Destination,
    builder: NavGraphBuilder.() -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startingDestination.route,
        builder = builder,
        enterTransition = {
            fadeIn()
        },
        exitTransition = {
            fadeOut()
        },
        popEnterTransition = {
            fadeIn()
        },
        popExitTransition = {
            fadeOut()
        }
    )
}