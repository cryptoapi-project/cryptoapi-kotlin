package io.pixelplex.model.response

import com.google.gson.annotations.SerializedName

data class EthTransactionsResponse (
    @SerializedName(ITEMS_KEY)
    val items: List<EthTransactionResponse>,

    @SerializedName(TOTAL_KEY)
    val total: Int
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
        const val TOTAL_KEY = "total"
        const val INTERNAL_TRANSACTIONS_KEY = "internal_transactions"
        const val CONFIRMATIONS_KEY = "confirmations"
        const val RECEIPT_KEY = "receipt"
    }
}

data class EthTransactionResponse (
    @SerializedName(EthTransactionsResponse.BLOCK_HASH_KEY)
    val blockHash: String,

    @SerializedName(EthTransactionsResponse.BLOCK_NUMBER_KEY)
    val blockNumber: Long,

    @SerializedName(EthTransactionsResponse.UTC_KEY)
    val utc: String,

    @SerializedName(EthTransactionsResponse.FROM_KEY)
    val from: String,

    @SerializedName(EthTransactionsResponse.GAS_KEY)
    val gas: Long,

    @SerializedName(EthTransactionsResponse.GAS_PRICE_KEY)
    val gasPrice: Long,

    @SerializedName(EthTransactionsResponse.HASH_KEY)
    val hash: String,

    @SerializedName(EthTransactionsResponse.INPUT_KEY)
    val input: String,

    @SerializedName(EthTransactionsResponse.NONCE_KEY)
    val nonce: Int,

    @SerializedName(EthTransactionsResponse.TO_KEY)
    val to: String,

    @SerializedName(EthTransactionsResponse.TRANSACTION_INDEX_KEY)
    val transactionIndex: Int,

    @SerializedName(EthTransactionsResponse.VALUE_KEY)
    val value: Long,

    @SerializedName(EthTransactionsResponse.V_KEY)
    val v: String,

    @SerializedName(EthTransactionsResponse.S_KEY)
    val s: String,

    @SerializedName(EthTransactionsResponse.R_KEY)
    val r: String,

    @SerializedName(EthTransactionsResponse.INTERNAL_TRANSACTIONS_KEY)
    val internalTransactions: List<String>,

    @SerializedName(EthTransactionsResponse.CONFIRMATIONS_KEY)
    val confirmations: Int?,

    @SerializedName(EthTransactionsResponse.RECEIPT_KEY)
    val receipt: Receipt?
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
    @SerializedName(EthTransactionResponse.CONTRACT_ADDRESS_KEY)
    val contractAddress: String? = null,

    @SerializedName(EthTransactionResponse.CUMULATIVE_GAS_USED_KEY)
    val cumulativeGasUsed: Long,

    @SerializedName(EthTransactionResponse.GAS_USED_KEY)
    val gasUsed: Long,

    @SerializedName(EthTransactionResponse.LOGS_KEY)
    val logs: List<String>,

    @SerializedName(EthTransactionResponse.STATUS_KEY)
    val status: Boolean
)