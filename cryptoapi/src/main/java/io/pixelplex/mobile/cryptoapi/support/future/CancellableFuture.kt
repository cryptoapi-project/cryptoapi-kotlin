package io.pixelplex.mobile.cryptoapi.support.future

interface CancellableFuture<T> : Cancellable, java.util.concurrent.Future<T> {

    /**
     * Set a callback to be invoked when this CancellableFuture completes.
     *
     * @param callback
     * @return
     */
    fun setCallback(callback: FutureCallback<T>): CancellableFuture<T>

    /**
     * Set a callback to be invoked when this CancellableFuture completes.
     *
     * @param callback
     * @param <C>
     * @return The callback
     */
    fun <C : FutureCallback<T>> then(callback: C): C

    /**
     * Get the result, if any. Returns null if still in progress.
     */
    fun tryGet(): T?

    /**
     * Get the exception, if any. Returns null if still in progress.
     */
    fun tryGetException(): Exception?

}