package io.chipmango.rating.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.chipmango.rating.repo.RatingRepo
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class RatingViewModel @Inject constructor(private val repo: RatingRepo) : ViewModel() {

    fun record() = repo.record()

    internal fun shouldShowRatingPopup(frequency: Int) = repo.showShowRatingPopup(frequency)
}

@Composable
internal fun shouldShowRating(
    frequency: Int,
    ratingViewModel: RatingViewModel = hiltViewModel()
): State<Boolean> {
    return produceState(initialValue = false, producer = {
        ratingViewModel.shouldShowRatingPopup(frequency).collect {
            this.value = it
        }
    })
}