package io.pixelplex.cryptoapi_android_framework.support.future

interface FutureCallback<T> {

    /**
     * Called by the CancellableFuture with the result or exception of the asynchronous operation.
     *
     * @param e Exception encountered by the operation
     * @param result Result returned from the operation
     */
    fun onCompleted(e: Exception?, result: T?)
}