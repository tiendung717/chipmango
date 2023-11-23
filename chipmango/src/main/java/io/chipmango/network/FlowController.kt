package io.chipmango.network

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class FlowController {
    var state: FlowState by mutableStateOf(FlowState.Pending)

    fun retry() {
        state = FlowState.Retry(System.currentTimeMillis())
    }
}

@Composable
fun rememberFlowController(initialState: FlowState = FlowState.Pending) = remember {
    val controller = FlowController()
    controller.state = initialState
    controller
}