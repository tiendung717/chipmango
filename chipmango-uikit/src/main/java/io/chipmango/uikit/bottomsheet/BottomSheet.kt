package io.chipmango.uikit.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppModalBottomSheetLayout(
    sheetContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    sheetController: BottomSheetController = rememberBottomSheetController(),
    sheetPeekHeight: Dp = 0.dp,
    sheetMaxWidth: Dp = BottomSheetDefaults.SheetMaxWidth,
    sheetShape: Shape = BottomSheetDefaults.ExpandedShape,
    sheetContainerColor: Color,
    sheetContentColor: Color = contentColorFor(sheetContainerColor),
    sheetTonalElevation: Dp = 0.dp,
    sheetShadowElevation: Dp = 16.dp,
    sheetDragHandle: @Composable (() -> Unit)? = { BottomSheetDefaults.DragHandle() },
    sheetSwipeEnabled: Boolean = true,
    topBar: @Composable (() -> Unit)? = null,
    snackbarHost: @Composable (SnackbarHostState) -> Unit = { SnackbarHost(it) },
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(containerColor),
    contentPadding: PaddingValues = PaddingValues(16.dp),
    content: @Composable (PaddingValues) -> Unit
) {
    BottomSheetScaffold(
        sheetContent = {
            Box(
                modifier = Modifier
                    .navigationBarsPadding()
                    .fillMaxWidth()
                    .padding(contentPadding)
            ) {
                sheetContent()
            }
        },
        modifier,
        sheetController.scaffoldState,
        sheetPeekHeight,
        sheetMaxWidth,
        sheetShape,
        sheetContainerColor,
        sheetContentColor,
        sheetTonalElevation,
        sheetShadowElevation,
        sheetDragHandle,
        sheetSwipeEnabled,
        topBar,
        snackbarHost,
        containerColor,
        contentColor,
        content
    )
}