package io.pixelplex.model.common

import com.google.gson.annotations.SerializedName

/**
 * Implementation of [CryptoApiResponse]
 * Combines some specific properties of Error response
 *
 * @author Sergey Krupenich
 */
data class ErrorResponse(
    @SerializedName("errors")
    val errors: List<Error>,

    @SerializedName("status")
    val status: Int
) : CryptoApiResponse

/**
 * Combines all fields of response error
 *
 * @author Sergey Krupenich
 */
data class Error(
    @SerializedName("message")
    val message: String,

    @SerializedName("field")
    val field: String? = null
)