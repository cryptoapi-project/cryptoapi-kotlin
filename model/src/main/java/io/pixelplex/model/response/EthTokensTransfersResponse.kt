package io.pixelplex.model.response

import com.google.gson.annotations.SerializedName

data class EthTokensTransfersResponse (
    @SerializedName(ADDRESSES_KEY)
    val addresses: List<String>,

    @SerializedName(LIMIT_KEY)
    val limit: Int,

    @SerializedName(SKIP_KEY)
    val skip: Int,

    @SerializedName(ITEMS_KEY)
    val items: List<EthTokensTransferResponse>,

    @SerializedName(COUNT_KEY)
    val count: Int,

    @SerializedName(ERRORS_KEY)
    val errors: List<ErrorResponse>? = null,

    @SerializedName(STATUS_KEY)
    val status: Int? = null
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
        const val ERRORS_KEY = "errors"
        const val STATUS_KEY = "status"
    }
}

data class EthTokensTransferResponse (
    @SerializedName(EthTokensTransfersResponse.TYPE_KEY)
    val type: String,

    @SerializedName(EthTokensTransfersResponse.EXECUTE_ADDRESS_KEY)
    val executeAddress: String,

    @SerializedName(EthTokensTransfersResponse.FROM_KEY)
    val from: String,

    @SerializedName(EthTokensTransfersResponse.TO_KEY)
    val to: String,

    @SerializedName(EthTokensTransfersResponse.VALUE_KEY)
    val value: String,

    @SerializedName(EthTokensTransfersResponse.ADDRESS_KEY)
    val address: String,

    @SerializedName(EthTokensTransfersResponse.BLOCK_NUMBER_KEY)
    val blockNumber: Long,

    @SerializedName(EthTokensTransfersResponse.TRANSACTION_HASH_KEY)
    val transactionHash: String,

    @SerializedName(EthTokensTransfersResponse.TRANSACTION_INDEX_KEY)
    val transactionIndex: Int,

    @SerializedName(EthTokensTransfersResponse.TIMESTAMP_KEY)
    val timestamp: String
)