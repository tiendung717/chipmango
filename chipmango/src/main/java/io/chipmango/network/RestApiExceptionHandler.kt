package io.chipmango.network

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun RestApiExceptionHandler(content: @Composable (Throwable, onClearException: () -> Unit) -> Unit) {
    val exception by NetworkException.exception.collectAsStateWithLifecycle()
    if (exception != null) {
        content(exception!!) {
            NetworkException.clear()
        }
    }
}