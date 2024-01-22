package io.chipmango.rating.inappreview

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import io.chipmango.rating.viewmodel.RatingViewModel
import io.chipmango.rating.viewmodel.shouldShowRating

@Composable
fun InAppReviewDialog(
    ratingViewModel: RatingViewModel = hiltViewModel(),
    frequency: Int
) {
    val activity = LocalContext.current as Activity
    val shouldShowRating by shouldShowRating(
        frequency = frequency,
        ratingViewModel = ratingViewModel
    )

    if (shouldShowRating) {
        LaunchedEffect(Unit) {
            InAppReview.execute(
                activity = activity,
                onSuccess = {},
                onFailure = {}
            )
        }
    }
}