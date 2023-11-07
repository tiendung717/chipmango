package com.chipmango.app

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import io.chipmango.permission.NotificationRequester
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(Modifier.fillMaxSize()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    NotificationRequester(
                        modifier = Modifier.align(Alignment.Center),
                        onPermissionGranted = {
                            Timber.tag("nt.dung").d("Granted!!!!")
                        },
                        explainDialog = { onPermissionRequest, onDismissRequest ->
                            Dialog(onDismissRequest = onDismissRequest) {
                                Button(onClick = onPermissionRequest) {
                                    Text(text = "Go to Settings")
                                }
                            }
                        },
                        content = { onPermissionRequest ->
                            Button(onClick = onPermissionRequest) {
                                Text(text = "Request Permission")
                            }
                        }
                    )
                }
            }
        }
    }
}