package io.pixelplex.model.data.eth

import com.google.gson.annotations.SerializedName
import io.pixelplex.model.common.CryptoApiResponse

/**
 * Implementation of [CryptoApiResponse]
 * Combines some specific properties of ETH estimated gas response
 *
 * @author Sergey Krupenich
 */
data class EthEstimatedGas(
    @SerializedName("estimate_gas")
    val estimateGas: Long,

    @SerializedName("gas_price")
    val gasPrice: Long,

    @SerializedName("nonce")
    val nonce: Int
) : CryptoApiResponse