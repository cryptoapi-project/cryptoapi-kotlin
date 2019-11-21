package io.pixelplex.model.response

import com.google.gson.annotations.SerializedName

/**
 * Implementation of [CryptoApiResponse]
 * Combines some specific properties of ETH estimated gas response
 *
 * @author Sergey Krupenich
 */
data class EthEstimatedGasResponse (
    @SerializedName(ESTIMATE_GAS_KEY)
    val estimateGas: Long,

    @SerializedName(GAS_PRICE_KEY)
    val gasPrice: Long,

    @SerializedName(NONCE_KEY)
    val nonce: Int
): CryptoApiResponse {
    companion object {
        const val ESTIMATE_GAS_KEY = "estimate_gas"
        const val GAS_PRICE_KEY = "gas_price"
        const val NONCE_KEY = "nonce"
    }
}