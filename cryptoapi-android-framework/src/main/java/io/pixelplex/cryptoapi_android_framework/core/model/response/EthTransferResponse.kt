package io.pixelplex.cryptoapi_android_framework.core.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EthTransferResponse (
    @SerializedName(ADDRESSES_KEY) @Expose
    val addresses: List<String>,

    @SerializedName(SKIP_KEY) @Expose
    val skip: Int,

    @SerializedName(LIMIT_KEY) @Expose
    val limit: Int,

    @SerializedName(ITEMS_KEY) @Expose
    val items: List<EthTransferItem>,

    @SerializedName(COUNT_KEY) @Expose
    val count: Int,

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
        const val ERRORS_KEY = "errors"
        const val STATUS_KEY = "status"
    }
}

data class EthTransferItem(
    @SerializedName(EthTransferResponse.BLOCK_NUMBER_KEY) @Expose
    val blockNumber: Long,

    @SerializedName(EthTransferResponse.UTC_KEY) @Expose
    val utc: String,

    @SerializedName(EthTransferResponse.FROM_KEY) @Expose
    val from: String,

    @SerializedName(EthTransferResponse.GAS_KEY) @Expose
    val gas: Long,

    @SerializedName(EthTransferResponse.HASH_KEY) @Expose
    val hash: String,

    @SerializedName(EthTransferResponse.TO_KEY) @Expose
    val to: String,

    @SerializedName(EthTransferResponse.VALUE_KEY) @Expose
    val value: Long,

    @SerializedName(EthTransferResponse.GAS_PRICE_KEY) @Expose
    val gasPrice: Long,

    @SerializedName(EthTransferResponse.INTERNAL_KEY) @Expose
    val internal: Boolean
)