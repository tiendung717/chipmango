package io.chipmango.permission

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ContentWithPermissions(
    permission: List<String>,
    content: @Composable () -> Unit,
    permissionsNotGrantedContent: @Composable (onRequestPermission: () -> Unit) -> Unit,
    permissionsNotAvailableContent: @Composable (onRequestPermission: () -> Unit) -> Unit,
) {
    val context = LocalContext.current
    val permissionState = rememberMultiplePermissionsState(permission)

    PermissionsRequired(
        multiplePermissionsState = permissionState,
        permissionsNotGrantedContent = {
            if (permissionState.shouldShowRationale) {
                permissionsNotGrantedContent {
                    permissionState.launchMultiplePermissionRequest()
                }
            } else {
                LaunchedEffect(Unit) {
                    permissionState.launchMultiplePermissionRequest()
                }
            }
        },
        permissionsNotAvailableContent = {
            permissionsNotAvailableContent {
                context.openAppSystemSettings()
            }
        },
        content = content
    )
}

private fun Context.openAppSystemSettings() {
    startActivity(
        Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", packageName, null)
        }
    )
}