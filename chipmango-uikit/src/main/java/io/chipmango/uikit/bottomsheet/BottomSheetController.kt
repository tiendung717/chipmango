package io.chipmango.uikit.bottomsheet


import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class BottomSheetController constructor(
    private val coroutineScope: CoroutineScope,
    val scaffoldState: BottomSheetScaffoldState,
) {
    fun show() {
        coroutineScope.launch {
            scaffoldState.bottomSheetState.expand()
        }
    }

    fun hide(onHide: () -> Unit = {}) {
        coroutineScope.launch {
            scaffoldState.bottomSheetState.hide()
        }.invokeOnCompletion { onHide() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberBottomSheetController(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    bottomSheetState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState()
) = remember {
    BottomSheetController(
        coroutineScope = coroutineScope,
        scaffoldState = bottomSheetState
    )
}