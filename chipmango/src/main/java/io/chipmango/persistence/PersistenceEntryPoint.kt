package io.chipmango.persistence

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope

@EntryPoint
@InstallIn(SingletonComponent::class)
internal interface PersistenceEntryPoint {
    fun persistent(): Persistence
    fun coroutine(): CoroutineScope
}