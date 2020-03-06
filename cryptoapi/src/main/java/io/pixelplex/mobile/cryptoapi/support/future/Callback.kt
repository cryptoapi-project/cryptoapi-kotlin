package io.pixelplex.mobile.cryptoapi.support.future

import io.pixelplex.mobile.cryptoapi.support.Result
import io.pixelplex.mobile.cryptoapi.support.error
import io.pixelplex.mobile.cryptoapi.support.value
import io.pixelplex.mobile.cryptoapi.model.exception.ApiException

interface Callback<T> {

    /**
     * Calls when operation is successful
     *
     * @param result Result of operation
     */
    fun onSuccess(result: T)

    /**
     * Calls when operation is failed
     *
     * @param error Error occurred during operation process
     */
    fun onError(error: ApiException)

}

/**
 * Wrap input code block in [Result] and and calls corresponding callback methods after finish
 */
fun <T> Callback<T>.processResult(block: () -> T) {
    Result { block() }
        .value { result -> this.onSuccess(result) }
        .error { error -> this.onError(ApiException.create(error)) }
}

/**
 * Calls corresponding callback methods on [targetResult] processing results
 */
fun <E : Exception, T> Callback<T>.processResult(targetResult: Result<E, T>) {
    targetResult
        .value { result -> this.onSuccess(result) }
        .error { error -> this.onError(ApiException.create(error)) }
}