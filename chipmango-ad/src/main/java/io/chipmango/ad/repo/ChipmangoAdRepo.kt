package io.chipmango.ad.repo

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChipmangoAdRepo @Inject constructor() {
    val isAdInitialized = MutableStateFlow<Boolean>(false)

    fun setAdInitialized(initialized: Boolean) {
        isAdInitialized.value = initialized
    }
}