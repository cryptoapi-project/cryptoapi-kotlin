package io.pixelplex.cryptoapi_android_framework.core.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EthTransactionsResponse (
    @SerializedName(ITEMS_KEY) @Expose
    val items: List<EthTransactionResponse>,

    @SerializedName(TOTAL_KEY) @Expose
    val total: Int,

    @SerializedName(ERRORS_KEY)
    val errors: List<ErrorResponse>? = null,

    @SerializedName(STATUS_KEY)
    val status: Int? = null
): CryptoApiResponse {
    companion object {
        const val ITEMS_KEY = "items"
        const val BLOCK_HASH_KEY = "block_hash"
        const val BLOCK_NUMBER_KEY = "block_number"
        const val FROM_KEY = "from"
        const val TO_KEY = "to"
        const val TRANSACTION_INDEX_KEY = "transaction_index"
        const val VALUE_KEY = "value"
        const val V_KEY = "v"
        const val S_KEY = "s"
        const val R_KEY = "r"
        const val HASH_KEY = "hash"
        const val INPUT_KEY = "input"
        const val NONCE_KEY = "nonce"
        const val GAS_KEY = "gas"
        const val GAS_PRICE_KEY = "gas_price"
        const val UTC_KEY = "utc"
        const val ERRORS_KEY = "errors"
        const val STATUS_KEY = "status"
        const val TOTAL_KEY = "total"
        const val INTERNAL_TRANSACTIONS_KEY = "internal_transactions"
        const val CONFIRMATIONS_KEY = "confirmations"
        const val RECEIPT_KEY = "receipt"
    }
}

data class EthTransactionResponse (
    @SerializedName(EthTransactionsResponse.BLOCK_HASH_KEY) @Expose
    val blockHash: String,

    @SerializedName(EthTransactionsResponse.BLOCK_NUMBER_KEY) @Expose
    val blockNumber: Long,

    @SerializedName(EthTransactionsResponse.UTC_KEY) @Expose
    val utc: String,

    @SerializedName(EthTransactionsResponse.FROM_KEY) @Expose
    val from: String,

    @SerializedName(EthTransactionsResponse.GAS_KEY) @Expose
    val gas: Long,

    @SerializedName(EthTransactionsResponse.GAS_PRICE_KEY) @Expose
    val gasPrice: Long,

    @SerializedName(EthTransactionsResponse.HASH_KEY) @Expose
    val hash: String,

    @SerializedName(EthTransactionsResponse.INPUT_KEY) @Expose
    val input: String,

    @SerializedName(EthTransactionsResponse.NONCE_KEY) @Expose
    val nonce: Int,

    @SerializedName(EthTransactionsResponse.TO_KEY) @Expose
    val to: String,

    @SerializedName(EthTransactionsResponse.TRANSACTION_INDEX_KEY) @Expose
    val transactionIndex: Int,

    @SerializedName(EthTransactionsResponse.VALUE_KEY) @Expose
    val value: Long,

    @SerializedName(EthTransactionsResponse.V_KEY) @Expose
    val v: String,

    @SerializedName(EthTransactionsResponse.S_KEY) @Expose
    val s: String,

    @SerializedName(EthTransactionsResponse.R_KEY) @Expose
    val r: String,

    @SerializedName(EthTransactionsResponse.INTERNAL_TRANSACTIONS_KEY) @Expose
    val internalTransactions: List<String>,

    @SerializedName(EthTransactionsResponse.CONFIRMATIONS_KEY)
    val confirmations: Int?,

    @SerializedName(EthTransactionsResponse.RECEIPT_KEY)
    val receipt: Receipt?,

    @SerializedName(EthTransactionsResponse.ERRORS_KEY)
    val errors: List<ErrorResponse>? = null,

    @SerializedName(STATUS_KEY)
    val status: Int? = null
): CryptoApiResponse {
    companion object {
        const val CONTRACT_ADDRESS_KEY = "contract_address"
        const val CUMULATIVE_GAS_USED_KEY = "cumulative_gas_used"
        const val GAS_USED_KEY = "gas_used"
        const val LOGS_KEY = "logs"
        const val STATUS_KEY = "status"
    }
}

data class Receipt (
    @SerializedName(EthTransactionResponse.CONTRACT_ADDRESS_KEY) @Expose
    val contractAddress: String? = null,

    @SerializedName(EthTransactionResponse.CUMULATIVE_GAS_USED_KEY) @Expose
    val cumulativeGasUsed: Long,

    @SerializedName(EthTransactionResponse.GAS_USED_KEY) @Expose
    val gasUsed: Long,

    @SerializedName(EthTransactionResponse.LOGS_KEY) @Expose
    val logs: List<String>,

    @SerializedName(EthTransactionResponse.STATUS_KEY) @Expose
    val status: Boolean
)