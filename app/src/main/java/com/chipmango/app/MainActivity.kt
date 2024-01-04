package com.chipmango.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.chipmango.app.theme.themeColors
import com.solid.test.BuildConfig
import dagger.hilt.android.AndroidEntryPoint
import io.chipmango.rating.AppRatingDialog
import io.chipmango.rating.viewmodel.RatingViewModel
import io.chipmango.theme.theme.AppTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val ratingViewModel: RatingViewModel by viewModels<RatingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val darkMode by remember {
                mutableStateOf(false)
            }



            AppTheme(useDarkTheme = darkMode) {
//                UiKitApp(containerColor = themeColors().background.Normal)

                Button(onClick = { ratingViewModel.record() }) {
                    Text(text = "Rating record")
                }

                AppRatingDialog(
                    ratingViewModel = ratingViewModel,
                    applicationId = BuildConfig.APPLICATION_ID,
                    appName = "Test",
                    email = "solid@gmail.com",
                    frequency = 3,
                    positiveButtonContainerColor = themeColors().project.Normal,
                    positiveButtonTextColor = themeColors().neutralInvert.Stronger,
                    negativeButtonTextColor = themeColors().text.Normal,
                    titleTextColor = themeColors().text.Stronger,
                    messageTextColor = themeColors().text.Normal,
                    questionImage = io.chipmango.rating.R.drawable.ic_rating,
                    ratingImage = io.chipmango.rating.R.drawable.ic_rating,
                    feedbackImage = io.chipmango.rating.R.drawable.ic_rating,
                    question = "Do you like our app?",
                    ratingTitle = "Rating title",
                    ratingMessage = "Rating message",
                    feedbackTitle = "Feedback title",
                    feedbackMessage = "Feedback message"
                )
            }
        }
    }
}