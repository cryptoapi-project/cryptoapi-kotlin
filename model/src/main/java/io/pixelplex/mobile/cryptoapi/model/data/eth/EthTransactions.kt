package io.pixelplex.mobile.cryptoapi.model.data.eth

import com.google.gson.annotations.SerializedName
import io.pixelplex.mobile.cryptoapi.model.common.CryptoApiResponse

/**
 * Implementation of [CryptoApiResponse]
 * Combines some specific properties of ETH transaction response
 *
 * @author Sergey Krupenich
 */
data class EthTransactions(
    @SerializedName("items")
    val items: List<EthTransaction>,

    @SerializedName("total")
    val total: Int
) : CryptoApiResponse

/**
 * Implementation of [CryptoApiResponse]
 * Combines all fields of ETH transaction response
 *
 * @author Sergey Krupenich
 */
data class EthTransaction(
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
    val r: String,

    @SerializedName("internal_transactions")
    val internalTransactions: List<InternalTransaction>,

    @SerializedName("confirmations")
    val confirmations: Int?,

    @SerializedName("receipt")
    val receipt: Receipt?
) : CryptoApiResponse

/**
 * Contains info about internal transaction item
 */
data class InternalTransaction (
    @SerializedName("to")
    val to: String?,

    @SerializedName("from")
    val from: String,

    @SerializedName("value")
    val value: String,

    @SerializedName("is_suicide")
    val is_suicide: Boolean,

    @SerializedName("type")
    val type: String
)

/**
 * Combines all fields of ETH receipt
 *
 * @author Sergey Krupenich
 */
data class Receipt(

    @SerializedName("block_hash")
    val blockHash: String,

    @SerializedName("block_number")
    val blockNumber: Int,

    @SerializedName("contract_address")
    val contractAddress: String? = null,

    @SerializedName("cumulative_gas_used")
    val cumulativeGasUsed: Long,

    @SerializedName("gas_used")
    val gasUsed: Long,

    @SerializedName("logs")
    val logs: List<Log>,

    @SerializedName("status")
    val status: Boolean
)

data class Log(
    @SerializedName("address")
    val address: String,

    @SerializedName("data")
    val data: String,

    @SerializedName("topics")
    val topics: List<String>,

    @SerializedName("log_index")
    val logIndex: Long,

    @SerializedName("transaction_hash")
    val transactionHash: String,

    @SerializedName("transaction_index")
    val transactionIndex: Long,

    @SerializedName("block_hash")
    val blockHash: String,

    @SerializedName("block_number")
    val blockNumber: Int
)