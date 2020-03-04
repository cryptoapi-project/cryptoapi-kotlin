package io.pixelplex.mobile.cryptoapi.support.future

import io.pixelplex.mobile.cryptoapi.support.toError
import io.pixelplex.mobile.cryptoapi.support.toValue
import io.pixelplex.mobile.cryptoapi.support.Result
import io.pixelplex.mobile.cryptoapi.model.exception.ApiException
import java.util.concurrent.TimeUnit

/**
 * Wraps future task result in [Result]
 *
 * Future result must be not null
 */
fun <E : Exception, T> FutureTask<T>.wrapResult(): Result<E, T> =
    try {
        val result = get()
        result!!.toValue()
    } catch (exception: Exception) {
        (exception as E).toError()
    }

/**
 * Wraps future task result in [Result] with [timeout]
 *
 * Future result must be not null
 */
fun <E : Exception, T> FutureTask<T>.wrapResult(timeout: Long, unit: TimeUnit): Result<E, T> =
    try {
        val result = get()
        result!!.toValue()
    } catch (exception: Exception) {
        (exception as E).toError()
    }

/**
 * Wraps future task result in [Result] with default value if operation succeeds with null
 */
fun <E : Exception, T> FutureTask<T>.wrapResult(default: T): Result<E, T> =
    try {
        val result = get() ?: default
        result.toValue()
    } catch (exception: Exception) {
        (exception as E).toError()
    }

/**
 * Creates [Callback] that completes future in it's methods with optional block call
 */
fun <T> FutureTask<T>.completeCallback(
    successBlock: (T) -> Unit = {},
    errorBlock: (ApiException) -> Unit = {}
): Callback<T> {
    return object : Callback<T> {
        override fun onSuccess(result: T) {
            successBlock(result)
            setComplete(result)
        }

        override fun onError(error: ApiException) {
            errorBlock(error)
            setComplete(error)
        }

    }
}