package io.chipmango.navigation.destination

import android.content.Intent
import android.net.Uri
import androidx.navigation.NavType
import androidx.navigation.navDeepLink

class DestinationFactory(private val scheme: String, private val host: String) {

    private fun buildDestinationUri(path: String, argumentKeys: List<String>): String {
        val uri = Uri.Builder()
            .scheme(scheme)
            .authority(host)
            .appendPath(path)
            .apply {
                argumentKeys.forEach { key ->
                    appendQueryParameter(key, "{${key}}")
                }
            }
            .build()
        return Uri.decode(uri.toString())
    }

    fun create(
        path: String,
        screenName: String,
        screenClass: String,
        vararg arguments: Pair<String, NavType<*>>
    ): Destination {
        val destinationUri = buildDestinationUri(
            path = path,
            argumentKeys = arguments.map { it.first }
        )

        return Destination(
            route = destinationUri,
            screenName = screenName,
            screenClass = screenClass,
            arguments = arguments.map { DestinationArgument(it.first, it.second) },
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = destinationUri
                    action = Intent.ACTION_VIEW
                }
            )
        )
    }
}