package io.pixelplex.model.response

import com.google.gson.annotations.SerializedName

/**
 * Implementation of [CryptoApiResponse]
 * Combines some specific properties of ETH token transfer response
 *
 * @author Sergey Krupenich
 */
data class EthTokenTransferResponse (
    @SerializedName(ADDRESSES_KEY)
    val addresses: List<String>,

    @SerializedName(LIMIT_KEY)
    val limit: Int,

    @SerializedName(SKIP_KEY)
    val skip: Int,

    @SerializedName(ITEMS_KEY)
    val items: List<EthTokensTransfer>,

    @SerializedName(COUNT_KEY)
    val count: Int
): CryptoApiResponse {
    companion object {
        const val ADDRESSES_KEY = "addresses"
        const val COUNT_KEY = "count"
        const val LIMIT_KEY = "limit"
        const val SKIP_KEY = "skip"
        const val ITEMS_KEY = "items"
        const val TYPE_KEY = "type"
        const val EXECUTE_ADDRESS_KEY = "execute_address"
        const val FROM_KEY = "from"
        const val TO_KEY = "to"
        const val VALUE_KEY = "value"
        const val ADDRESS_KEY = "address"
        const val BLOCK_NUMBER_KEY = "block_number"
        const val TRANSACTION_HASH_KEY = "transaction_hash"
        const val TRANSACTION_INDEX_KEY = "transaction_index"
        const val TIMESTAMP_KEY = "timestamp"
    }
}

/**
 * Combines all fields of ETH token
 *
 * @author Sergey Krupenich
 */
data class EthTokensTransfer (
    @SerializedName(EthTokenTransferResponse.TYPE_KEY)
    val type: String,

    @SerializedName(EthTokenTransferResponse.EXECUTE_ADDRESS_KEY)
    val executeAddress: String,

    @SerializedName(EthTokenTransferResponse.FROM_KEY)
    val from: String,

    @SerializedName(EthTokenTransferResponse.TO_KEY)
    val to: String,

    @SerializedName(EthTokenTransferResponse.VALUE_KEY)
    val value: String,

    @SerializedName(EthTokenTransferResponse.ADDRESS_KEY)
    val address: String,

    @SerializedName(EthTokenTransferResponse.BLOCK_NUMBER_KEY)
    val blockNumber: Long,

    @SerializedName(EthTokenTransferResponse.TRANSACTION_HASH_KEY)
    val transactionHash: String,

    @SerializedName(EthTokenTransferResponse.TRANSACTION_INDEX_KEY)
    val transactionIndex: Int,

    @SerializedName(EthTokenTransferResponse.TIMESTAMP_KEY)
    val timestamp: String
)