package io.pixelplex.cryptoapi_android_framework.core.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EstimatedGasResponse (
    @SerializedName(ESTIMATE_GAS_KEY) @Expose
    val estimateGas: Long,

    @SerializedName(GAS_PRICE_KEY) @Expose
    val gasPrice: Long,

    @SerializedName(NONCE_KEY) @Expose
    val nonce: Int,

    @SerializedName(ERRORS_KEY)
    val errors: List<ErrorResponse>?,

    @SerializedName(STATUS_KEY)
    val status: Int
): CryptoApiResponse {
    companion object {
        const val ESTIMATE_GAS_KEY = "estimate_gas"
        const val GAS_PRICE_KEY = "gas_price"
        const val NONCE_KEY = "nonce"
        const val ERRORS_KEY = "errors"
        const val STATUS_KEY = "status"
    }
}