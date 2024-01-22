package io.chipmango.rating.inappreview

import android.app.Activity
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.testing.FakeReviewManager
import timber.log.Timber

object InAppReview {

    fun execute(activity: Activity, onSuccess: () -> Unit, onFailure: () -> Unit) {
        val reviewManager = ReviewManagerFactory.create(activity)
        reviewManager.requestReviewFlow()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val reviewInfo = task.result
                    launchReviewFlow(activity, reviewManager, reviewInfo, onSuccess, onFailure)
                } else {
                    Timber.e(task.exception)
                    onFailure()
                }
            }
    }

    private fun launchReviewFlow(activity: Activity, reviewManager: ReviewManager, reviewInfo: ReviewInfo, onSuccess: () -> Unit, onFailure: () -> Unit) {
        reviewManager.launchReviewFlow(activity, reviewInfo)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Timber.d("Review flow launched successfully!")
                    onSuccess()
                } else {
                    Timber.e(task.exception)
                    onFailure()
                }
            }
    }
}