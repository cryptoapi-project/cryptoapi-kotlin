package io.pixelplex.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName(MESSAGE_KEY)
    val message: String,

    @SerializedName(FIELD_KEY)
    val field: String? = null,

    @SerializedName(VALUE_KEY)
    val value: String? = null
): CryptoApiResponse {
    companion object {
        const val MESSAGE_KEY = "message"
        const val FIELD_KEY = "field"
        const val VALUE_KEY = "value"
    }
}