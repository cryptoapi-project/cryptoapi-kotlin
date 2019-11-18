package io.pixelplex.cryptoapi_android_framework.exception

open class LocalException : RuntimeException {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(cause: Throwable?) : this(cause?.message, cause)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}