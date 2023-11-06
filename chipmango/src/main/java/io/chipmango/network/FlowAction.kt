package io.chipmango.network

sealed class FlowAction {
    data object Pending: FlowAction()
    data class Retry(val timestamp: Long) : FlowAction()

    companion object {
        fun retry() = Retry(System.currentTimeMillis())
    }
}