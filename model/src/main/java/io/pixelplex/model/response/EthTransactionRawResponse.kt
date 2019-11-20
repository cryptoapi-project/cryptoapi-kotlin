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

data class EthTransactionRawDecodeResponse(
    @SerializedName(NONCE_KEY)
    val nonce: Long? = null,

    @SerializedName(GAS_PRICE_KEY)
    val gasPrice: EthHex? = null,

    @SerializedName(GAS_LIMIT_KEY)
    val gasLimit: EthHex? = null,

    @SerializedName(TO_KEY)
    val to: String? = null,

    @SerializedName(VALUE_KEY)
    val value: EthHex? = null,

    @SerializedName(DATA_KEY)
    val data: String? = null,

    @SerializedName(V_KEY)
    val v: Int? = null,

    @SerializedName(R_KEY)
    val r: String? = null,

    @SerializedName(S_KEY)
    val s: String? = null,

    @SerializedName(EthTransactionRawResponse.ERRORS_KEY)
    val errors: List<ErrorResponse>? = null,

    @SerializedName(EthTransactionRawResponse.STATUS_KEY)
    val status: Int? = null
): CryptoApiResponse {
    companion object {
        const val NONCE_KEY = "nonce"
        const val GAS_PRICE_KEY = "gasPrice"
        const val GAS_LIMIT_KEY = "gasLimit"
        const val TO_KEY = "to"
        const val VALUE_KEY = "value"
        const val DATA_KEY = "data"
        const val V_KEY = "v"
        const val R_KEY = "r"
        const val S_KEY = "s"
        const val HEX_KEY = "_hex"
    }
}

data class EthHex (
    @SerializedName(EthTransactionRawDecodeResponse.HEX_KEY)
    val hex: String
)