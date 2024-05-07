package io.chipmango.navigation.navigator

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import io.chipmango.navigation.destination.Destination
import io.chipmango.navigation.destination.DestinationArgument

internal fun Destination.withArguments(arguments: Map<String, Any?> = emptyMap()): String {
    val uri = Uri.parse(route)
    val newUri = Uri.Builder()
        .scheme(uri.scheme)
        .authority(uri.authority)
        .apply {
            uri.pathSegments.forEach {
                appendPath(it)
            }
        }
        .apply {
            arguments.forEach {
                appendQueryParameter(it.key, "${it.value}")
            }
        }
        .build()
    return Uri.decode(newUri.toString())
}

fun NavController.navigate(
    destination: Destination,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
    arguments: Bundle? = null,
) {
    val destinationArgs = mutableMapOf<String, Any?>()
    arguments?.let {
        it.keySet().forEach() { key ->
            destinationArgs[key] = it.get(key)
        }
    }

    navigate(
        route = destination.withArguments(destinationArgs),
        navOptions = navOptions,
        navigatorExtras = navigatorExtras,
    )
}

fun NavBackStackEntry.getRequiredLong(key: String): Long {
    val value = arguments?.getLong(key)
    return value ?: throw IllegalArgumentException("Parameter $key not found")
}

fun NavBackStackEntry.getRequiredInt(key: String): Int {
    val value = arguments?.getInt(key)
    return value ?: throw IllegalArgumentException("Parameter $key not found")
}

fun NavBackStackEntry.getRequiredString(key: String): String {
    val value = arguments?.getString(key)
    return value ?: throw IllegalArgumentException("Parameter $key not found")
}

fun NavBackStackEntry.getRequiredBoolean(key: String): Boolean {
    val value = arguments?.getBoolean(key)
    return value ?: throw IllegalArgumentException("Parameter $key not found")
}

fun NavBackStackEntry.getRequiredFloat(key: String): Float {
    val value = arguments?.getFloat(key)
    return value ?: throw IllegalArgumentException("Parameter $key not found")
}

fun NavBackStackEntry.getRequiredDouble(key: String): Double {
    val value = arguments?.getDouble(key)
    return value ?: throw IllegalArgumentException("Parameter $key not found")
}