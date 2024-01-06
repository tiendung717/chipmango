package io.chipmango.rating

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi

import android.view.MotionEvent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.gowtham.ratingbar.ComposeStars
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.RatingBarUtils
import com.gowtham.ratingbar.RatingBarUtils.stepSized
import com.gowtham.ratingbar.StepSize

/**
 * @param value is current selected rating count
 * @param numStars Sets the number of stars to show.
 * @param size for each star
 * @param padding for set padding to each star
 * @param isIndicator Whether this rating bar is only an indicator
 * @param activeColor A [Color] representing an active star (or part of it)
 * @param inactiveColor A [Color] representing a inactive star (or part of it)
 * @param stepSize Minimum value to trigger a change
 * @param ratingBarStyle Can be [RatingBarStyle.Normal] or [RatingBarStyle.HighLighted]
 * @param onRatingChanged A function to be called when the click or drag is released and rating value is passed
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppRatingBar(
    modifier: Modifier = Modifier,
    value: Float = 0f,
    numStars: Int = 5,
    size: Dp = 26.dp,
    padding: Dp = 2.dp,
    isIndicator: Boolean = false,
    activeColor: Color = Color(0xffffd740),
    inactiveColor: Color = Color(0xffffecb3),
    stepSize: StepSize = StepSize.ONE,
    ratingBarStyle: RatingBarStyle = RatingBarStyle.Normal,
    onRatingChanged: (Float) -> Unit
) {
    var rowSize by remember { mutableStateOf(Size.Zero) }
    var rating by remember { mutableStateOf(value) }

    Row(modifier = modifier
        .onSizeChanged { rowSize = it.toSize() }
        .pointerInteropFilter {
            if (isIndicator)
                return@pointerInteropFilter false
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    //handling when single click happens
                    val calculatedStars =
                        RatingBarUtils.calculateStars(
                            it.x, rowSize.width,
                            numStars, padding.value.toInt()
                        )
                    rating = calculatedStars.stepSized(stepSize)
                }

                MotionEvent.ACTION_MOVE -> {
                    //handling while dragging event
                    val x1 = it.x.coerceIn(0f, rowSize.width)
                    val calculatedStars =
                        RatingBarUtils.calculateStars(
                            x1, rowSize.width,
                            numStars, padding.value.toInt()
                        )
                    rating = calculatedStars.stepSized(stepSize)
                }

                MotionEvent.ACTION_UP -> {
                    //when the click or drag is released
                    onRatingChanged(rating)
                }
            }
            true
        }) {
        ComposeStars(
            rating, numStars, size, padding, activeColor,
            inactiveColor, ratingBarStyle
        )
    }

}