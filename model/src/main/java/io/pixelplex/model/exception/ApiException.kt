package io.pixelplex.model.exception

import java.io.IOException

open class ApiException : IOException {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(cause: Throwable?) : this(cause?.message, cause)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}