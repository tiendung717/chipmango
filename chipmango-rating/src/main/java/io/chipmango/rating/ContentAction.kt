package io.chipmango.rating

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun ContentAction(
    modifier: Modifier,
    @DrawableRes image: Int,
    title: String,
    message: String,
    positiveText: String,
    titleTextColor: Color,
    messageTextColor: Color,
    positiveButtonContainerColor: Color,
    positiveButtonTextColor: Color,
    negativeButtonTextColor: Color,
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit,
    titleTextStyle: TextStyle,
    messageTextStyle: TextStyle,
    buttonTextStyle: TextStyle
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.width(130.dp),
            painter = painterResource(id = image),
            contentDescription = null
        )

        Text(
            text = title,
            style = titleTextStyle,
            color = titleTextColor
        )

        Text(
            text = message,
            style = messageTextStyle,
            color = messageTextColor,
            textAlign = TextAlign.Center
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onPositiveClick,
            colors = ButtonDefaults.buttonColors(
                contentColor = positiveButtonTextColor,
                containerColor = positiveButtonContainerColor
            ),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                modifier = Modifier,
                text = positiveText,
                style = buttonTextStyle,
                color = positiveButtonTextColor
            )
        }

        TextButton(modifier = Modifier.fillMaxWidth(), onClick = onNegativeClick) {
            Text(
                modifier = Modifier,
                text = "Not now",
                style = buttonTextStyle,
                color = negativeButtonTextColor
            )
        }
    }
}

internal fun Context.rateApp(applicationId: String) {
    val uri = Uri.parse("market://details?id=$applicationId")
    val backupUri =
        Uri.parse("http://play.google.com/store/apps/details?id=$applicationId")
    try {
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    } catch (ex: Exception) {
        startActivity(Intent(Intent.ACTION_VIEW, backupUri))
    }
}

internal fun Context.sendFeedback(email: String, appName: String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/email"
    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
    intent.putExtra(Intent.EXTRA_SUBJECT, "[$appName] Feedback")
    startActivity(Intent.createChooser(intent, "Send Feedback:"))
}