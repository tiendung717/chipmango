package io.chipmango.network

import timber.log.Timber

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()

    data class Error(val throwable: Throwable? = null) : ResultWrapper<Nothing>()

    data object None : ResultWrapper<Nothing>()

    data object Loading : ResultWrapper<Nothing>()

    companion object {
        fun <T> success(value: T) = Success(value)
        fun loading() = Loading
        fun none() = None
        fun error(throwable: Throwable?): Error {
            Timber.e(throwable)
            return Error(throwable)
        }
    }

    @Throws(Exception::class)
    fun takeValueOrThrow(): T {
        return when (this) {
            is Success -> value
            is Error -> throw throwable ?: Throwable()
            else -> throw Throwable("Unknown the result type $this")
        }
    }
}

fun <T> ResultWrapper<T>.isLoading() = this is ResultWrapper.Loading

fun <T> ResultWrapper<T>.isSuccess() = this is ResultWrapper.Success

fun <T> ResultWrapper<T>.isError() = this is ResultWrapper.Error

fun <T> ResultWrapper<T>.takeThrowable() = (this as ResultWrapper.Error).throwable

fun <T> ResultWrapper<T>.takeValue() = (this as ResultWrapper.Success).value

