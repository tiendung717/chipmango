package io.chipmango.ad.repo

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChipmangoAdViewModel @Inject constructor(private val repo: ChipmangoAdRepo) : ViewModel() {

    fun isAdInitialized() = repo.isAdInitialized
}