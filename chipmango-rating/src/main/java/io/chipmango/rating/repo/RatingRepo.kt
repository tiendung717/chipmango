package io.chipmango.rating.repo

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RatingRepo @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        private const val KEY_RATE_SHOW_COUNT = "key_rate_count"
    }

    private val Context.dataStore by preferencesDataStore(name = "rating.ds")
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    fun record() {
        coroutineScope.launch {
            val count = runBlocking { read(KEY_RATE_SHOW_COUNT, 0).first() }
            save(KEY_RATE_SHOW_COUNT, count + 1)
        }
    }

    internal fun showShowRatingPopup(frequency: Int): Flow<Boolean> {
        return read(KEY_RATE_SHOW_COUNT, 0).map { it > 0 && it % frequency == 0 }
    }

    private suspend fun save(key: String, value: Int) {
        context.dataStore.edit { settings ->
            val prefKey = intPreferencesKey(key)
            settings[prefKey] = value
        }
    }

    private fun read(key: String, defaultValue: Int): Flow<Int> {
        val prefKey = intPreferencesKey(key)
        return context.dataStore.data.map { it[prefKey] ?: defaultValue }
    }
}