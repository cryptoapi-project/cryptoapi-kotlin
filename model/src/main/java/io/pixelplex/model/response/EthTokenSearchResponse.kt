package io.pixelplex.model.response

import com.google.gson.annotations.SerializedName

/**
 * Implementation of [CryptoApiResponse]
 * Combines some specific properties of ETH token search result response
 *
 * @author Sergey Krupenich
 */
data class EthTokenSearchResponse (
    @SerializedName(QUERY_KEY)
    val query: String,

    @SerializedName(SKIP_KEY)
    val skip: Int,

    @SerializedName(LIMIT_KEY)
    val limit: Int,

    @SerializedName(COUNT_KEY)
    val count: Int,

    @SerializedName(TYPES_KEY)
    val types: List<String>,

    @SerializedName(ITEMS_KEY)
    val items: List<EthTokenSearch>
): CryptoApiResponse {
    companion object {
        const val QUERY_KEY = "query"
        const val SKIP_KEY = "skip"
        const val LIMIT_KEY = "limit"
        const val COUNT_KEY = "count"
        const val TYPES_KEY = "types"
        const val ITEMS_KEY = "items"
        const val ADDRESS_KEY = "address"
        const val CREATE_TRANSACTION_HASH_KEY = "create_transaction_hash"
        const val INFO_KEY = "info"
        const val TYPE_KEY = "type"
        const val DECIMALS_KEY = "decimals"
        const val TOTAL_SUPPLY_KEY = "totalSupply"
        const val SYMBOL_KEY = "symbol"
        const val NAME_KEY = "name"
        const val STATUS_KEY = "status"
    }
}

/**
 * Combines all fields of ETH token search result
 *
 * @author Sergey Krupenich
 */
data class EthTokenSearch (
    @SerializedName(EthTokenSearchResponse.ADDRESS_KEY)
    val address: String,

    @SerializedName(EthTokenSearchResponse.CREATE_TRANSACTION_HASH_KEY)
    val createTransactionHash: String,

    @SerializedName(EthTokenSearchResponse.INFO_KEY)
    val info: EthTokenSearchInfo,

    @SerializedName(EthTokenSearchResponse.STATUS_KEY)
    val status: Boolean,

    @SerializedName(EthTokenSearchResponse.TYPE_KEY)
    val type: String
)

/**
 * Combines all fields of ETH token search result info
 *
 * @author Sergey Krupenich
 */
data class EthTokenSearchInfo (
    @SerializedName(EthTokenSearchResponse.DECIMALS_KEY)
    val decimals: String,

    @SerializedName(EthTokenSearchResponse.TOTAL_SUPPLY_KEY)
    val totalSupply: String,

    @SerializedName(EthTokenSearchResponse.SYMBOL_KEY)
    val symbol: String,

    @SerializedName(EthTokenSearchResponse.NAME_KEY)
    val name: String
)