package io.pixelplex.mobile.cryptoapi.model.data.eth

import com.google.gson.annotations.SerializedName
import io.pixelplex.mobile.cryptoapi.model.common.CryptoApiResponse

/**
 * Implementation of [CryptoApiResponse]
 * Combines some specific properties of ETH external transaction response
 *
 * @author Sergey Krupenich
 */
data class EthTransactionExternal(
    @SerializedName("addresses")
    val addresses: List<String>,

    @SerializedName("limit")
    val limit: Int,

    @SerializedName("skip")
    val skip: Int,

    @SerializedName("count")
    val count: Int,

    @SerializedName("items")
    val items: List<EthTransactionExternalItem>
) : CryptoApiResponse

/**
 * Implementation of [CryptoApiResponse]
 * Combines fields of ETH external transaction
 *
 * @author Sergey Krupenich
 */
data class EthTransactionExternalItem(
    @SerializedName("block_hash")
    val blockHash: String,

    @SerializedName("block_number")
    val blockNumber: Long,

    @SerializedName("utc")
    val utc: String,

    @SerializedName("from")
    val from: String,

    @SerializedName("gas")
    val gas: Long,

    @SerializedName("gas_price")
    val gasPrice: Long,

    @SerializedName("hash")
    val hash: String,

    @SerializedName("input")
    val input: String,

    @SerializedName("nonce")
    val nonce: Int,

    @SerializedName("to")
    val to: String,

    @SerializedName("transaction_index")
    val transactionIndex: Int,

    @SerializedName("value")
    val value: Long,

    @SerializedName("v")
    val v: String,

    @SerializedName("s")
    val s: String,

    @SerializedName("r")
    val r: String
)