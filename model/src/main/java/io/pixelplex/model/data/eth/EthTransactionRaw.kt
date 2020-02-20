package io.pixelplex.model.data.eth

import com.google.gson.annotations.SerializedName
import io.pixelplex.model.common.CryptoApiResponse

/**
 * Implementation of [CryptoApiResponse]
 * Combines some specific properties of ETH raw transaction decoded response
 *
 * @author Sergey Krupenich
 */
data class EthTransactionRawDecodeResponse(
    @SerializedName("nonce")
    val nonce: Long? = null,

    @SerializedName("gas_price")
    val gasPrice: String? = null,

    @SerializedName("gas_limit")
    val gasLimit: String? = null,

    @SerializedName("to")
    val to: String? = null,

    @SerializedName("value")
    val value: String? = null,

    @SerializedName("data")
    val data: String? = null,

    @SerializedName("v")
    val v: Int? = null,

    @SerializedName("r")
    val r: String? = null,

    @SerializedName("s")
    val s: String? = null
) : CryptoApiResponse