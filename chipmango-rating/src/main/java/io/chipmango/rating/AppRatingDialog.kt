package io.chipmango.rating

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import io.chipmango.rating.viewmodel.RatingViewModel
import io.chipmango.rating.viewmodel.shouldShowRating

sealed class RateStep {
    data object AskQuestion : RateStep()
    data object Positive : RateStep()
    data object Negative : RateStep()
}

@Composable
fun AppRatingDialog(
    ratingViewModel: RatingViewModel = hiltViewModel(),
    applicationId: String,
    appName: String,
    email: String,
    frequency: Int,
    positiveButtonContainerColor: Color,
    positiveButtonTextColor: Color,
    negativeButtonTextColor: Color,
    titleTextColor: Color,
    messageTextColor: Color,
    @DrawableRes questionImage: Int,
    @DrawableRes ratingImage: Int,
    @DrawableRes feedbackImage: Int,
    question: String,
    ratingTitle: String,
    ratingMessage: String,
    feedbackTitle: String,
    feedbackMessage: String
) {
    val context = LocalContext.current
    var rateStep: RateStep by remember {
        mutableStateOf(RateStep.AskQuestion)
    }
    var dismissed by remember {
        mutableStateOf(false)
    }

    val shouldShowRating by shouldShowRating(
        frequency = frequency,
        ratingViewModel = ratingViewModel
    )

    LaunchedEffect(shouldShowRating) {
        dismissed = false
    }

    if (shouldShowRating && !dismissed) {
        Dialog(
            onDismissRequest = {
                dismissed = true
            }
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color.White
            ) {
                AnimatedContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    targetState = rateStep, label = ""
                ) { step ->
                    when (step) {
                        RateStep.AskQuestion -> {
                            ContentAskQuestion(
                                modifier = Modifier.fillMaxWidth(),
                                contentColor = titleTextColor,
                                positiveButtonContainerColor = positiveButtonContainerColor,
                                positiveButtonColor = positiveButtonTextColor,
                                onPositiveClick = {
                                    rateStep = RateStep.Positive
                                },
                                onNegativeClick = {
                                    rateStep = RateStep.Negative
                                },
                                question = question,
                                questionImage = questionImage
                            )
                        }

                        RateStep.Positive -> {
                            ContentAction(
                                modifier = Modifier.fillMaxWidth(),
                                image = ratingImage,
                                title = ratingTitle,
                                message = ratingMessage,
                                titleTextColor = titleTextColor,
                                messageTextColor = messageTextColor,
                                positiveButtonTextColor = positiveButtonTextColor,
                                negativeButtonTextColor = negativeButtonTextColor,
                                onPositiveClick = { context.rateApp(applicationId) },
                                onNegativeClick = {
                                    dismissed = true
                                },
                                positiveButtonContainerColor = positiveButtonContainerColor,
                                positiveText = "Rate us on the Playstore"
                            )
                        }

                        RateStep.Negative -> {
                            ContentAction(
                                modifier = Modifier.fillMaxWidth(),
                                image = feedbackImage,
                                title = feedbackTitle,
                                message = feedbackMessage,
                                titleTextColor = titleTextColor,
                                messageTextColor = messageTextColor,
                                positiveButtonTextColor = positiveButtonTextColor,
                                negativeButtonTextColor = negativeButtonTextColor,
                                onPositiveClick = { context.sendFeedback(email, appName) },
                                onNegativeClick = { dismissed = true },
                                positiveButtonContainerColor = positiveButtonContainerColor,
                                positiveText = "Send feedback"
                            )
                        }
                    }
                }
            }
        }
    }
}