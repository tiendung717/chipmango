package io.chipmango.network

sealed class FlowState {
    data object Pending: FlowState()
    data class Retry(val timestamp: Long) : FlowState()
}