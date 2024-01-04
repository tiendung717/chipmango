package io.chipmango.rating

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.chipmango.theme.typography.UIKitTypography

@Composable
fun ContentAskQuestion(
    modifier: Modifier,
    contentColor: Color,
    negativeButtonColor: Color,
    positiveButtonColor: Color,
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.rating_question),
            style = UIKitTypography.Body1Regular16,
            color = contentColor
        )

        Image(
            modifier = Modifier.width(130.dp),
            painter = painterResource(id = R.drawable.ic_rating),
            contentDescription = null
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        onNegativeClick()
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                text = stringResource(R.string.very_dissatisfied),
                style = UIKitTypography.Body1Regular16,
                color = negativeButtonColor
            )

            Text(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        onPositiveClick()
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                text = stringResource(R.string.very_satisfied),
                style = UIKitTypography.Body1Medium16,
                color = positiveButtonColor
            )
        }
    }
}

@Preview
@Composable
private fun PreviewContentAskQuestion() {
    ContentAskQuestion(
        modifier = Modifier.fillMaxWidth(),
        contentColor = Color.Black,
        negativeButtonColor = Color.Gray,
        positiveButtonColor = Color.Red,
        onPositiveClick = {

        },
        onNegativeClick = {

        }
    )
}