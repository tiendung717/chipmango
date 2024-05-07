package io.chipmango.navigation.helper

import androidx.navigation.NavBackStackEntry

fun NavBackStackEntry.argumentString(key: String, defValue: String = "") =
    arguments?.getString(key, defValue) ?: defValue

fun NavBackStackEntry.argumentInt(key: String, defValue: Int = 0) =
    arguments?.getString(key)?.toIntOrNull() ?: defValue

fun NavBackStackEntry.argumentLong(key: String, defValue: Long = 0L) =
    arguments?.getString(key)?.toLongOrNull() ?: defValue

fun NavBackStackEntry.argumentBoolean(key: String, defValue: Boolean = false) =
    arguments?.getString(key)?.toBoolean() ?: defValue
