package io.pixelplex.model.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName(ERRORS_KEY)
    val errors: List<Error>,

    @SerializedName(STATUS_KEY)
    val status: Int
) : CryptoApiResponse {
    companion object {
        const val MESSAGE_KEY = "message"
        const val FIELD_KEY = "field"
        const val STATUS_KEY = "status"
        const val ERRORS_KEY = "errors"
    }
}

data class Error (
    @SerializedName(ErrorResponse.MESSAGE_KEY)
    val message: String,

    @SerializedName(ErrorResponse.FIELD_KEY)
    val field: String? = null
)