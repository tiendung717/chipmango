package io.chipmango.ad

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.chipmango.ad.repo.ChipmangoAdRepo
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdViewModel @Inject constructor(private val chipmangoAds: ChipmangoAds) : ViewModel() {

    fun initialize(activity: Activity, isAdEnabled: suspend () -> Boolean) {
        viewModelScope.launch {
            if (isAdEnabled()) {
                chipmangoAds.init(activity)
            }
        }
    }

}