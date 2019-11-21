package io.pixelplex.model.response

import com.google.gson.annotations.SerializedName

data class EthTokenInfoResponse (
    @SerializedName(ADDRESS_KEY)
    val address: String,

    @SerializedName(TYPE_KEY)
    val type: String,

    @SerializedName(NAME_KEY)
    val name: String,

    @SerializedName(SYMBOL_KEY)
    val symbol: String,

    @SerializedName(DECIMALS_KEY)
    val decimals: String,

    @SerializedName(TOTAL_SUPPLY_KEY)
    val totalSupply: String,

    @SerializedName(CREATE_TRANSACTION_HASH_KEY)
    val createTransactionHash: String,

    @SerializedName(HOLDERS_COUNT_KEY)
    val holdersCount: Int
): CryptoApiResponse {
    companion object {
        const val ADDRESS_KEY = "address"
        const val TYPE_KEY = "type"
        const val NAME_KEY = "name"
        const val SYMBOL_KEY = "symbol"
        const val DECIMALS_KEY = "decimals"
        const val TOTAL_SUPPLY_KEY = "totalSupply"
        const val CREATE_TRANSACTION_HASH_KEY = "create_transaction_hash"
        const val HOLDERS_COUNT_KEY = "holders_count"
    }
}