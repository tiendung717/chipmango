package io.chipmango.rating

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

sealed class RateStep {
    data object AskQuestion: RateStep()
    data object Positive: RateStep()
    data object Negative: RateStep()
}

@Composable
fun AppRatingDialog(onDismiss: () -> Unit) {
    var rateStep: RateStep by remember {
        mutableStateOf(RateStep.AskQuestion)
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color.White
        ) {
            AnimatedContent(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                targetState = rateStep, label = "") { step ->
                when (step) {
                    RateStep.AskQuestion -> {
                        ContentAskQuestion(
                            modifier = Modifier.fillMaxWidth(),
                            contentColor = Color.Black,
                            negativeButtonColor = Color.Gray,
                            positiveButtonColor = Color.Red,
                            onPositiveClick = {
                                rateStep = RateStep.Positive
                            },
                            onNegativeClick = {
                                rateStep = RateStep.Negative
                            }
                        )
                    }
                    RateStep.Positive -> {
                        ContentAction(
                            modifier = Modifier.fillMaxWidth(),
                            image = R.drawable.ic_rating,
                            title = "Your opinion matters to us!",
                            message = "We coded and designed with all our might,\n" +
                                    "Please leave a positive review, make our day bright!\n" +
                                    "Five stars would be oh so appreciated,\n" +
                                    "More features and updates, expedited!",
                            titleTextColor = Color.Black,
                            messageTextColor = Color.Gray,
                            positiveButtonTextColor = Color.White,
                            negativeButtonTextColor = Color.Gray,
                            onPositiveClick = { /*TODO*/ },
                            onNegativeClick = {},
                            positiveButtonContainerColor = Color.Blue
                        )
                    }
                    RateStep.Negative -> {
                        ContentAction(
                            modifier = Modifier.fillMaxWidth(),
                            image = R.drawable.ic_rating,
                            title = "Your opinion matters to us!",
                            message = "Would you mind telling us what made you unpleasant?",
                            titleTextColor = Color.Black,
                            messageTextColor = Color.Gray,
                            positiveButtonTextColor = Color.White,
                            negativeButtonTextColor = Color.Gray,
                            onPositiveClick = { /*TODO*/ },
                            onNegativeClick = {},
                            positiveButtonContainerColor = Color.Blue
                        )
                    }
                }
            }
        }

    }
}