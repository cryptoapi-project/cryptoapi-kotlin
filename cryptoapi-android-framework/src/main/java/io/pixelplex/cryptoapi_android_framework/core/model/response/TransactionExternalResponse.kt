package io.pixelplex.cryptoapi_android_framework.core.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TransactionExternalResponse (
    @SerializedName(ADDRESSES_KEY)
    val addresses: List<String>,

    @SerializedName(LIMIT_KEY)
    val limit: Int,

    @SerializedName(SKIP_KEY)
    val skip: Int,

    @SerializedName(COUNT_KEY)
    val count: Int,

    @SerializedName(ITEMS_KEY)
    val items: List<EthTransactionExternal>,

    @SerializedName(ERRORS_KEY)
    val errors: List<ErrorResponse>? = null,

    @SerializedName(STATUS_KEY)
    val status: Int? = null
): CryptoApiResponse {
    companion object {
        const val ADDRESSES_KEY = "addresses"
        const val LIMIT_KEY = "limit"
        const val SKIP_KEY = "skip"
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
        const val COUNT_KEY = "count"
        const val ERRORS_KEY = "errors"
        const val STATUS_KEY = "status"
    }
}

data class EthTransactionExternal (
    @SerializedName(TransactionExternalResponse.BLOCK_HASH_KEY)
    val blockHash: String,

    @SerializedName(TransactionExternalResponse.BLOCK_NUMBER_KEY)
    val blockNumber: Long,

    @SerializedName(TransactionExternalResponse.UTC_KEY)
    val utc: String,

    @SerializedName(TransactionExternalResponse.FROM_KEY)
    val from: String,

    @SerializedName(TransactionExternalResponse.GAS_KEY)
    val gas: Long,

    @SerializedName(TransactionExternalResponse.GAS_PRICE_KEY)
    val gasPrice: Long,

    @SerializedName(TransactionExternalResponse.HASH_KEY)
    val hash: String,

    @SerializedName(TransactionExternalResponse.INPUT_KEY)
    val input: String,

    @SerializedName(TransactionExternalResponse.NONCE_KEY)
    val nonce: Int,

    @SerializedName(TransactionExternalResponse.TO_KEY)
    val to: String,

    @SerializedName(TransactionExternalResponse.TRANSACTION_INDEX_KEY)
    val transactionIndex: Int,

    @SerializedName(TransactionExternalResponse.VALUE_KEY)
    val value: Long,

    @SerializedName(TransactionExternalResponse.V_KEY)
    val v: String,

    @SerializedName(TransactionExternalResponse.S_KEY)
    val s: String,

    @SerializedName(TransactionExternalResponse.R_KEY)
    val r: String
)