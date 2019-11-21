package io.pixelplex.model.response

import com.google.gson.annotations.SerializedName

data class EthTransactionExternalResponse (
    @SerializedName(ADDRESSES_KEY)
    val addresses: List<String>,

    @SerializedName(LIMIT_KEY)
    val limit: Int,

    @SerializedName(SKIP_KEY)
    val skip: Int,

    @SerializedName(COUNT_KEY)
    val count: Int,

    @SerializedName(ITEMS_KEY)
    val items: List<EthTransactionExternal>
): CryptoApiResponse {
    companion object {
        const val ADDRESSES_KEY = "typedParams"
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
    }
}

data class EthTransactionExternal (
    @SerializedName(EthTransactionExternalResponse.BLOCK_HASH_KEY)
    val blockHash: String,

    @SerializedName(EthTransactionExternalResponse.BLOCK_NUMBER_KEY)
    val blockNumber: Long,

    @SerializedName(EthTransactionExternalResponse.UTC_KEY)
    val utc: String,

    @SerializedName(EthTransactionExternalResponse.FROM_KEY)
    val from: String,

    @SerializedName(EthTransactionExternalResponse.GAS_KEY)
    val gas: Long,

    @SerializedName(EthTransactionExternalResponse.GAS_PRICE_KEY)
    val gasPrice: Long,

    @SerializedName(EthTransactionExternalResponse.HASH_KEY)
    val hash: String,

    @SerializedName(EthTransactionExternalResponse.INPUT_KEY)
    val input: String,

    @SerializedName(EthTransactionExternalResponse.NONCE_KEY)
    val nonce: Int,

    @SerializedName(EthTransactionExternalResponse.TO_KEY)
    val to: String,

    @SerializedName(EthTransactionExternalResponse.TRANSACTION_INDEX_KEY)
    val transactionIndex: Int,

    @SerializedName(EthTransactionExternalResponse.VALUE_KEY)
    val value: Long,

    @SerializedName(EthTransactionExternalResponse.V_KEY)
    val v: String,

    @SerializedName(EthTransactionExternalResponse.S_KEY)
    val s: String,

    @SerializedName(EthTransactionExternalResponse.R_KEY)
    val r: String
)