package io.pixelplex.mobile.cryptoapi.model.exception

import java.io.IOException

open class LocalException : RuntimeException {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(cause: Throwable?) : this(cause?.message, cause)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}

open class NetworkException: IOException()