package io.chipmango.navigation.destination

import android.content.Intent
import android.net.Uri
import androidx.navigation.navDeepLink

class DestinationFactory(private val scheme: String, private val host: String) {

    private fun buildRoute(path: String, queryParameters: Array<out String>): String {
        val uri = Uri.Builder()
            .scheme(scheme)
            .authority(host)
            .appendPath(path)
            .apply {
                queryParameters.forEach {
                    appendQueryParameter(it, "{${it}}")
                }
            }
            .build()
        return Uri.decode(uri.toString())
    }

    fun create(
        path: String,
        vararg queryParameters: String
    ): Destination {
        val url = buildRoute(path, queryParameters)
        return Destination(
            route = url,
            arguments = queryParameters.toList(),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = url
                    action = Intent.ACTION_VIEW
                }
            )
        )
    }
}