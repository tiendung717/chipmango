package io.chipmango.rating

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gowtham.ratingbar.RatingBar

@Composable
internal fun ContentAskQuestion(
    modifier: Modifier,
    question: String,
    @DrawableRes questionImage: Int,
    contentColor: Color,
    positiveButtonColor: Color,
    positiveButtonContainerColor: Color,
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit,
    titleTextStyle: TextStyle,
    buttonTextStyle: TextStyle
) {
    var rating: Float by remember { mutableFloatStateOf(4f) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier.width(130.dp),
            painter = painterResource(id = questionImage),
            contentDescription = null
        )

        Text(
            text = question,
            style = titleTextStyle,
            color = contentColor
        )

        RatingBar(
            modifier = Modifier,
            value = rating,
            onRatingChanged = {
                rating = it
            }
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (rating >= 4f) onPositiveClick()
                else onNegativeClick()
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = positiveButtonColor,
                containerColor = positiveButtonContainerColor
            ),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                modifier = Modifier,
                text = stringResource(R.string.submit),
                style = buttonTextStyle,
                color = positiveButtonColor
            )
        }

    }
}