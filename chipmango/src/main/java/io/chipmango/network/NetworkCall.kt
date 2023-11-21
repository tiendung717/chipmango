package io.chipmango.network

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import timber.log.Timber

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    call: suspend CoroutineScope.() -> T
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(call())
        } catch (e: Throwable) {
            Timber.e(e)
            NetworkException.push(e)
            ResultWrapper.error(e)
        }
    }
}


fun <T> flowApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    dataQuery: suspend () -> T
): Flow<ResultWrapper<T>> {
    return flow {
        emit(ResultWrapper.Loading)
        emit(ResultWrapper.Success(dataQuery()))
    }
        .catch {
            Timber.e(it)
            NetworkException.push(it)
            emit(ResultWrapper.Error(it))
        }
        .flowOn(dispatcher)
}

@Composable
fun <T> rememberFlow(
    controller: FlowController = rememberFlowController(),
    initialValue: T,
    flowApi: () -> Flow<T>
) = remember(key1 = controller.state) {
    if (controller.state is FlowState.Pending) flow { emit(initialValue) }
    else flowApi.invoke()
}

@Composable
fun <T> ResultWrapperCollector(
    resultWrapper: ResultWrapper<T>,
    onSuccess: (T) -> Unit,
    onError: (Throwable?) -> Unit = {}
) {
    LaunchedEffect(resultWrapper) {
        when {
            resultWrapper.isSuccess() -> onSuccess(resultWrapper.takeValue())
            resultWrapper.isError() -> onError(resultWrapper.takeThrowable())
        }
    }
}