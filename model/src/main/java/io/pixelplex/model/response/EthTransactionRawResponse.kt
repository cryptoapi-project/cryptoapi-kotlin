package io.pixelplex.model.response

import com.google.gson.annotations.SerializedName
import io.pixelplex.model.response.CryptoApiResponse
import io.pixelplex.model.response.ErrorResponse

data class EthTransactionRawResponse (
    @SerializedName(HASH_KEY)
    val hash: String? = null,

    @SerializedName(ERRORS_KEY)
    val errors: List<ErrorResponse>? = null,

    @SerializedName(STATUS_KEY)
    val status: Int? = null
): CryptoApiResponse {
    companion object {
        const val HASH_KEY = "hash"
        const val ERRORS_KEY = "errors"
        const val STATUS_KEY = "status"
    }
}