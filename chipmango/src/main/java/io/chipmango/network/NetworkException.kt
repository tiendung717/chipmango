package io.chipmango.network

import kotlinx.coroutines.flow.MutableStateFlow

object NetworkException  {
    internal val exception = MutableStateFlow<Throwable?>(null)

    internal fun push(error: Throwable?) {
        exception.value = error
    }

    internal fun clear() {
        exception.value = null
    }
}