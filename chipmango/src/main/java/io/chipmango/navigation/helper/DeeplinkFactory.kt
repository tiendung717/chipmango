package io.chipmango.navigation.helper

import android.net.Uri
import io.chipmango.navigation.destination.Destination

object DeeplinkFactory {
    fun create(targetDestination: Destination, queryParams: Map<String, Any?>) : String {
        val uri = Uri.parse(targetDestination.route)
        val newUri = Uri.Builder()
            .scheme(uri.scheme)
            .authority(uri.authority)
            .apply {
                uri.pathSegments.forEach {
                    appendPath(it)
                }
            }
            .apply {
                queryParams.forEach {
                    appendQueryParameter(it.key, "${it.value}")
                }
            }
            .build()
        return Uri.decode(newUri.toString())
    }
}