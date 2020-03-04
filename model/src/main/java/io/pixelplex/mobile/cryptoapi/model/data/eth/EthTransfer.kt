package io.pixelplex.mobile.cryptoapi.model.data.eth

import com.google.gson.annotations.SerializedName
import io.pixelplex.mobile.cryptoapi.model.common.CryptoApiResponse

/**
 * Implementation of [CryptoApiResponse]
 * Combines some specific properties of ETH transfer response
 *
 * @author Sergey Krupenich
 */
data class EthTransfer(
    @SerializedName("addresses")
    val addresses: List<String>,

    @SerializedName("skip")
    val skip: Int,

    @SerializedName("limit")
    val limit: Int,

    @SerializedName("items")
    val items: List<EthTransferItem>,

    @SerializedName("count")
    val count: Int
) : CryptoApiResponse

/**
 * Combines all fields of ETH transfer
 *
 * @author Sergey Krupenich
 */
data class EthTransferItem(
    @SerializedName("block_number")
    val blockNumber: Long,

    @SerializedName("utc")
    val utc: String,

    @SerializedName("from")
    val from: String,

    @SerializedName("gas")
    val gas: Long,

    @SerializedName("hash")
    val hash: String,

    @SerializedName("to")
    val to: String,

    @SerializedName("value")
    val value: String,

    @SerializedName("gas_price")
    val gasPrice: Long,

    @SerializedName("internal")
    val internal: Boolean
)