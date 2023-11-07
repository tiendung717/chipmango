package io.chipmango.permission

import android.Manifest
import android.content.Intent
import android.net.Uri
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
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NotificationRequester(
    modifier: Modifier,
    onPermissionGranted: () -> Unit,
    explainDialog: @Composable (onRequestPermission: () -> Unit, onDismissRequest: () -> Unit) -> Unit,
    content: @Composable BoxScope.(onRequestPermission: () -> Unit) -> Unit
) {
    val context = LocalContext.current
    val notificationPermissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionState(
            permission = Manifest.permission.POST_NOTIFICATIONS,
        )
    } else {
        remember {
            object : PermissionState {
                override val hasPermission: Boolean
                    get() = true
                override val permission: String
                    get() = ""
                override val permissionRequested: Boolean
                    get() = true
                override val shouldShowRationale: Boolean
                    get() = false
                override fun launchPermissionRequest() {

                }
            }
        }
    }

    val settingIntent =  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        remember {
            Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                .putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
        }
    } else {
        remember {
            Intent().apply {
                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                data = Uri.fromParts("package", context.packageName, null)
            }
        }
    }

    val settingLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            if (notificationPermissionState.hasPermission || NotificationManagerCompat.from(context)
                    .areNotificationsEnabled()
            ) {
                onPermissionGranted()
            }
        }
    )

    var showExplainDialog by remember {
        mutableStateOf(false)
    }

    Box(modifier = modifier) {
        content {
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
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

                else -> {
                    if (!NotificationManagerCompat.from(context).areNotificationsEnabled()) {
                        settingLauncher.launch(settingIntent)
                    } else {
                        onPermissionGranted()
                    }
                }
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