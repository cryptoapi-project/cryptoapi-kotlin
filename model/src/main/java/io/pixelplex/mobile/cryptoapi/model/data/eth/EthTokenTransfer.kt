package io.pixelplex.mobile.cryptoapi.model.data.eth

import com.google.gson.annotations.SerializedName
import io.pixelplex.mobile.cryptoapi.model.common.CryptoApiResponse

/**
 * Implementation of [CryptoApiResponse]
 * Combines some specific properties of ETH token transfer response
 *
 * @author Sergey Krupenich
 */
data class EthTokenTransfer(
    @SerializedName("addresses")
    val addresses: List<String>,

    @SerializedName("limit")
    val limit: Int,

    @SerializedName("skip")
    val skip: Int,

    @SerializedName("items")
    val items: List<EthTokensTransferItem>,

    @SerializedName("count")
    val count: Int
) : CryptoApiResponse

/**
 * Combines all fields of ETH token
 *
 * @author Sergey Krupenich
 */
data class EthTokensTransferItem(
    @SerializedName("type")
    val type: String,

    @SerializedName("execute_address")
    val executeAddress: String,

    @SerializedName("from")
    val from: String,

    @SerializedName("to")
    val to: String,

    @SerializedName("value")
    val value: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("block_number")
    val blockNumber: Long,

    @SerializedName("transaction_hash")
    val transactionHash: String,

    @SerializedName("transaction_index")
    val transactionIndex: Int,

    @SerializedName("timestamp")
    val timestamp: String,

    @SerializedName("log_index")
    val logIndex: Long,

    @SerializedName("utc")
    val utc: String
)