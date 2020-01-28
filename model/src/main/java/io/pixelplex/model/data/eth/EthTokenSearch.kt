package io.pixelplex.model.data.eth

import com.google.gson.annotations.SerializedName
import io.pixelplex.model.common.CryptoApiResponse

/**
 * Implementation of [CryptoApiResponse]
 * Combines some specific properties of ETH token search result response
 *
 * @author Sergey Krupenich
 */
data class EthTokenSearch(
    @SerializedName("query")
    val query: String,

    @SerializedName("skip")
    val skip: Int,

    @SerializedName("limit")
    val limit: Int,

    @SerializedName("count")
    val count: Int,

    @SerializedName("types")
    val types: List<String>,

    @SerializedName("items")
    val items: List<EthTokenSearchItem>
) : CryptoApiResponse

/**
 * Combines all fields of ETH token search result
 *
 * @author Sergey Krupenich
 */
data class EthTokenSearchItem(
    @SerializedName("address")
    val address: String,

    @SerializedName("create_transaction_hash")
    val createTransactionHash: String,

    @SerializedName("info")
    val info: EthTokenSearchInfo,

    @SerializedName("status")
    val status: Boolean,

    @SerializedName("type")
    val type: String
)

/**
 * Combines all fields of ETH token search result info
 *
 * @author Sergey Krupenich
 */
data class EthTokenSearchInfo(
    @SerializedName("decimals")
    val decimals: String,

    @SerializedName("totalSupply")
    val totalSupply: String,

    @SerializedName("symbol")
    val symbol: String,

    @SerializedName("name")
    val name: String
)