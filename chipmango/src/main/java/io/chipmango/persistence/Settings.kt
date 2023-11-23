package io.chipmango.persistence

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Setting<T>(val key: String, val default: T)

@Composable
fun Setting<Boolean>.booleanState(waitForFirstValue: Boolean = false): State<Boolean> {
    val context = LocalContext.current
    val initialValue = if (waitForFirstValue) runBlocking { readBoolean(context).first() }
    else default

    return produceState(initialValue = initialValue) {
        readBoolean(context).collect { value = it }
    }
}

@Composable
fun Setting<Int>.intState(waitForFirstValue: Boolean = false): State<Int> {
    val context = LocalContext.current
    val initialValue = if (waitForFirstValue) runBlocking { readInt(context).first() }
    else default
    return produceState(initialValue = initialValue) {
        readInt(context).collect { value = it }
    }
}

@Composable
fun Setting<Long>.longState(waitForFirstValue: Boolean = false): State<Long> {
    val context = LocalContext.current
    val initialValue = if (waitForFirstValue) runBlocking { readLong(context).first() }
    else default
    return produceState(initialValue = initialValue) {
        readLong(context).collect { value = it }
    }
}

@Composable
fun Setting<Float>.floatState(): State<Float> {
    val context = LocalContext.current
    return produceState(initialValue = default) {
        readFloat(context).collect { value = it }
    }
}

@Composable
fun Setting<String>.stringState(waitForFirstValue: Boolean = false): State<String> {
    val context = LocalContext.current
    val initialValue = if (waitForFirstValue) runBlocking { readString(context).first() }
    else default
    return produceState(initialValue = initialValue) {
        readString(context).collect { value = it }
    }
}

@Composable
fun Setting<Set<String>>.setStringState(): State<Set<String>> {
    val context = LocalContext.current
    return produceState(initialValue = default) {
        readSetString(context).collect { value = it }
    }
}

fun Setting<Boolean>.readBoolean(context: Context) =
    EntryPointAccessors.fromApplication(context, PersistenceEntryPoint::class.java)
        .persistent()
        .read(key, default)


fun Setting<Boolean>.saveBoolean(context: Context, value: Boolean) {
    val entryPoint = EntryPointAccessors.fromApplication(context, PersistenceEntryPoint::class.java)
    entryPoint.coroutine().launch {
        entryPoint.persistent().save(key, value)
    }
}


fun Setting<Int>.readInt(context: Context) =
    EntryPointAccessors.fromApplication(context, PersistenceEntryPoint::class.java)
        .persistent()
        .read(key, default)

fun Setting<Long>.readLong(context: Context) =
    EntryPointAccessors.fromApplication(context, PersistenceEntryPoint::class.java)
        .persistent()
        .read(key, default)


fun Setting<Int>.saveInt(context: Context, value: Int) {
    val entryPoint = EntryPointAccessors.fromApplication(context, PersistenceEntryPoint::class.java)
    entryPoint.coroutine().launch {
        entryPoint.persistent().save(key, value)
    }
}

fun Setting<Long>.saveLong(context: Context, value: Long) {
    val entryPoint = EntryPointAccessors.fromApplication(context, PersistenceEntryPoint::class.java)
    entryPoint.coroutine().launch {
        entryPoint.persistent().save(key, value)
    }
}

fun Setting<Float>.readFloat(context: Context) =
    EntryPointAccessors.fromApplication(context, PersistenceEntryPoint::class.java)
        .persistent()
        .read(key, default)


fun Setting<Float>.saveFloat(context: Context, value: Float) {
    val entryPoint = EntryPointAccessors.fromApplication(context, PersistenceEntryPoint::class.java)
    entryPoint.coroutine().launch {
        entryPoint.persistent().save(key, value)
    }
}

fun Setting<String>.readString(context: Context) =
    EntryPointAccessors.fromApplication(context, PersistenceEntryPoint::class.java)
        .persistent()
        .read(key, default)


fun Setting<String>.saveString(context: Context, value: String) {
    val entryPoint = EntryPointAccessors.fromApplication(context, PersistenceEntryPoint::class.java)
    entryPoint.coroutine().launch {
        entryPoint.persistent().save(key, value)
    }
}

fun Setting<Set<String>>.readSetString(context: Context) =
    EntryPointAccessors.fromApplication(context, PersistenceEntryPoint::class.java)
        .persistent()
        .read(key, default)


fun Setting<Set<String>>.saveSetString(context: Context, value: Set<String>) {
    val entryPoint = EntryPointAccessors.fromApplication(context, PersistenceEntryPoint::class.java)
    entryPoint.coroutine().launch {
        entryPoint.persistent().save(key, value)
    }
}