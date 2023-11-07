package io.chipmango.permission

import android.Manifest
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationManagerCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NotificationRequester(
    modifier: Modifier,
    onPermissionGranted: () -> Unit,
    explainDialog: @Composable (onRequestPermission: () -> Unit, onDismissRequest: () -> Unit) -> Unit,
    content: @Composable BoxScope.(onRequestPermission: () -> Unit) -> Unit
) {
    val context = LocalContext.current
    val notificationPermissionState = rememberPermissionState(
        permission = Manifest.permission.POST_NOTIFICATIONS,
    )
    val settingIntent = remember {
        Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
            .putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
    }
    val settingLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            if (notificationPermissionState.hasPermission || NotificationManagerCompat.from(context).areNotificationsEnabled()) {
                onPermissionGranted()
            }
        }
    )

    var showExplainDialog by remember {
        mutableStateOf(false)
    }

    Box(modifier = modifier) {
        content {
            if (!notificationPermissionState.hasPermission) {
                if (notificationPermissionState.shouldShowRationale) {
                    showExplainDialog = true
                } else {
                    notificationPermissionState.launchPermissionRequest()
                }
            } else {
                onPermissionGranted()
            }
        }
    }

    if (showExplainDialog) {
        explainDialog(
            {
                settingLauncher.launch(settingIntent)
            },
            {
                showExplainDialog = false
            }
        )
    }

}