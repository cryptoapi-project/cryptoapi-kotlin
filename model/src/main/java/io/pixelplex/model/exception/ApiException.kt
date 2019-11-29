package io.pixelplex.model.exception

import io.pixelplex.model.response.Error
import io.pixelplex.model.response.ErrorResponse
import java.io.IOException

open class ApiException private constructor(val errors: List<Error>, val status: Int) :
    IOException() {
    companion object {

        fun create(errorResponse: ErrorResponse): ApiException {
            return ApiException(errorResponse.errors, errorResponse.status)
        }

        fun create(errors: List<Error>, status: Int): ApiException {
            return ApiException(errors, status)
        }

        fun create(exception: Exception): ApiException {
            return ApiException(
                listOf(Error(exception.message ?: "")),
                0
            )
        }
    }
}