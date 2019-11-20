package io.pixelplex.cryptoapi_android_framework.core.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TransactionExternalResponse (
    @SerializedName(ADDRESSES_KEY) @Expose
    val addresses: List<String>,

    @SerializedName(LIMIT_KEY) @Expose
    val limit: Int,

    @SerializedName(SKIP_KEY) @Expose
    val skip: Int,

    @SerializedName(COUNT_KEY) @Expose
    val count: Int,

    @SerializedName(ITEMS_KEY) @Expose
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
    @SerializedName(TransactionExternalResponse.BLOCK_HASH_KEY) @Expose
    val blockHash: String,

    @SerializedName(TransactionExternalResponse.BLOCK_NUMBER_KEY) @Expose
    val blockNumber: Long,

    @SerializedName(TransactionExternalResponse.UTC_KEY) @Expose
    val utc: String,

    @SerializedName(TransactionExternalResponse.FROM_KEY) @Expose
    val from: String,

    @SerializedName(TransactionExternalResponse.GAS_KEY) @Expose
    val gas: Long,

    @SerializedName(TransactionExternalResponse.GAS_PRICE_KEY) @Expose
    val gasPrice: Long,

    @SerializedName(TransactionExternalResponse.HASH_KEY) @Expose
    val hash: String,

    @SerializedName(TransactionExternalResponse.INPUT_KEY) @Expose
    val input: String,

    @SerializedName(TransactionExternalResponse.NONCE_KEY) @Expose
    val nonce: Int,

    @SerializedName(TransactionExternalResponse.TO_KEY) @Expose
    val to: String,

    @SerializedName(TransactionExternalResponse.TRANSACTION_INDEX_KEY) @Expose
    val transactionIndex: Int,

    @SerializedName(TransactionExternalResponse.VALUE_KEY) @Expose
    val value: Long,

    @SerializedName(TransactionExternalResponse.V_KEY) @Expose
    val v: String,

    @SerializedName(TransactionExternalResponse.S_KEY) @Expose
    val s: String,

    @SerializedName(TransactionExternalResponse.R_KEY) @Expose
    val r: String
)