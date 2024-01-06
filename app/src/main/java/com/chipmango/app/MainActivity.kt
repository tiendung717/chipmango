package com.chipmango.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.chipmango.app.theme.themeColors
import com.solid.test.BuildConfig
import dagger.hilt.android.AndroidEntryPoint
import io.chipmango.rating.AppRatingDialog
import io.chipmango.rating.viewmodel.RatingViewModel
import io.chipmango.theme.theme.AppTheme
import io.chipmango.theme.typography.UIKitTypography
import com.solid.test.R

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
                    appName = stringResource(id = R.string.app_name),
                    email = "solidsoft.apps@gmail.com",
                    frequency = 1,
                    positiveButtonContainerColor = MaterialTheme.colorScheme.tertiary,
                    positiveButtonTextColor = Color.White,
                    negativeButtonTextColor = MaterialTheme.colorScheme.onBackground,
                    titleTextColor = MaterialTheme.colorScheme.onBackground,
                    messageTextColor = MaterialTheme.colorScheme.onBackground,
                    questionImage = R.drawable.ic_rating_question,
                    ratingImage = io.chipmango.rating.R.drawable.ic_rating,
                    feedbackImage = R.drawable.ic_rating_feedback,
                    question = "How satisfied are you with Teleprompter?",
                    ratingTitle = "Your opinion matters to us!",
                    ratingMessage = "We've toiled and we've strove, to make this app thrive. A positive rating, that's all we ask, to complete our task.",
                    feedbackTitle = "Your opinion matters to us!",
                    feedbackMessage = "Would you mind telling us what made you unpleasant?",
                    titleTextStyle = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    messageTextStyle = MaterialTheme.typography.bodyLarge,
                    buttonTextStyle = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}