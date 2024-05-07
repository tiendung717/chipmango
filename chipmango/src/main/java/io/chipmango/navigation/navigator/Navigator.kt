package io.chipmango.navigation.navigator

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.util.Consumer
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
    val activity = LocalContext.current as AppCompatActivity
    DisposableEffect(navController) {
        val consumer = Consumer<Intent> {
            navController.handleDeepLink(it)
        }
        activity.addOnNewIntentListener(consumer)
        onDispose {
            activity.removeOnNewIntentListener(consumer)
        }
    }

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