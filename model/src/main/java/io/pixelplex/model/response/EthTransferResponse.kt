package io.pixelplex.model.response

import com.google.gson.annotations.SerializedName

data class EthTransferResponse (
    @SerializedName(ADDRESSES_KEY)
    val addresses: List<String>,

    @SerializedName(SKIP_KEY)
    val skip: Int,

    @SerializedName(LIMIT_KEY)
    val limit: Int,

    @SerializedName(ITEMS_KEY)
    val items: List<EthTransfer>,

    @SerializedName(COUNT_KEY)
    val count: Int
): CryptoApiResponse {
    companion object {
        const val ADDRESSES_KEY = "typedParams"
        const val LIMIT_KEY = "limit"
        const val SKIP_KEY = "skip"
        const val ITEMS_KEY = "items"
        const val BLOCK_NUMBER_KEY = "block_number"
        const val FROM_KEY = "from"
        const val TO_KEY = "to"
        const val VALUE_KEY = "value"
        const val HASH_KEY = "hash"
        const val GAS_KEY = "gas"
        const val GAS_PRICE_KEY = "gas_price"
        const val INTERNAL_KEY = "internal"
        const val UTC_KEY = "utc"
        const val COUNT_KEY = "count"
    }
}

data class EthTransfer(
    @SerializedName(EthTransferResponse.BLOCK_NUMBER_KEY)
    val blockNumber: Long,

    @SerializedName(EthTransferResponse.UTC_KEY)
    val utc: String,

    @SerializedName(EthTransferResponse.FROM_KEY)
    val from: String,

    @SerializedName(EthTransferResponse.GAS_KEY)
    val gas: Long,

    @SerializedName(EthTransferResponse.HASH_KEY)
    val hash: String,

    @SerializedName(EthTransferResponse.TO_KEY)
    val to: String,

    @SerializedName(EthTransferResponse.VALUE_KEY)
    val value: Long,

    @SerializedName(EthTransferResponse.GAS_PRICE_KEY)
    val gasPrice: Long,

    @SerializedName(EthTransferResponse.INTERNAL_KEY)
    val internal: Boolean
)