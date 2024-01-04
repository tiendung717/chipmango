package io.chipmango.uikit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.chipmango.uikit.bottomsheet.AppModalBottomSheetLayout
import io.chipmango.uikit.bottomsheet.rememberBottomSheetController
import io.chipmango.uikit.container.TitleContainer
import io.chipmango.uikit.datetime.DatePicker
import io.chipmango.uikit.datetime.TimePicker
import io.chipmango.uikit.divider.Separator
import io.chipmango.uikit.row.TwoLineRow
import io.chipmango.uikit.scaffold.AppScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UiKitApp(
    containerColor: Color
) {
    val bottomSheetController = rememberBottomSheetController()
    AppModalBottomSheetLayout(
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Red)
            )
        },
        sheetContainerColor = Color.White,
        sheetController = bottomSheetController
    ) {
        AppScaffold(containerColor = containerColor) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { DatePicker() }

                item { Separator(color = Color.Gray) }

                item { TimePicker() }

                item { Separator(color = Color.Gray) }

                item {
                    TitleContainer(
                        modifier = Modifier.fillMaxWidth(),
                        title = "TitleContainer -> TwoLineRow",
                        contentPadding = PaddingValues(0.dp),
                        titleTextColor = Color.Black
                    ) {
                        TwoLineRow(
                            modifier = Modifier.fillMaxWidth(),
                            title = "Title",
                            description = "Description",
                            titleTextColor = Color.Black,
                            descriptionTextColor = Color.Gray
                        )
                    }
                }

                item { Separator(color = Color.Gray) }

                item {
                    Button(onClick = { bottomSheetController.show() }) {
                        Text(text = "Show Bottom sheet!")
                    }
                }

                item { Separator(color = Color.Gray) }
            }
        }
    }

}