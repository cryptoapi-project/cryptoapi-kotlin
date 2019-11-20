package io.pixelplex.cryptoapi_android_framework.core.model.response

import com.google.gson.annotations.SerializedName

data class EthCallContractResponse (
    val response: String? = null,

    @SerializedName(ERRORS_KEY)
    val errors: List<ErrorResponse>? = null,

    @SerializedName(STATUS_KEY)
    val status: Int? = null
): CryptoApiResponse {
    companion object {
        const val ERRORS_KEY = "errors"
        const val STATUS_KEY = "status"
    }
}