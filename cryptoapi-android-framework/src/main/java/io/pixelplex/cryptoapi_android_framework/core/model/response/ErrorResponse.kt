package io.pixelplex.cryptoapi_android_framework.core.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName(MESSAGE_KEY) @Expose
    val message: String,

    @SerializedName(FIELD_KEY) @Expose
    val field: String
): CryptoApiResponse {
    companion object {
        const val MESSAGE_KEY = "message"
        const val FIELD_KEY = "field"
    }
}