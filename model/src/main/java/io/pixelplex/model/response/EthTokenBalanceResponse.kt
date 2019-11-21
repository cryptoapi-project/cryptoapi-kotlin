package io.pixelplex.model.response

import com.google.gson.annotations.SerializedName

/**
 * Implementation of [CryptoApiResponse]
 * Combines some specific properties of ETH token balance response
 *
 * @author Sergey Krupenich
 */
data class EthTokenBalanceResponse (
    @SerializedName(ITEMS_KEY)
    val items: List<EthTokensBalance>,

    @SerializedName(TOTAL_KEY)
    val total: Int
): CryptoApiResponse {
    companion object {
        const val TOTAL_KEY = "total"
        const val ADDRESS_KEY = "address"
        const val BALANCE_KEY = "balance"
        const val ITEMS_KEY = "items"
    }
}

/**
 * Implementation of [CryptoApiResponse]
 * Combines all fields ETH token balance
 *
 * @author Sergey Krupenich
 */
data class EthTokensBalance (
    @SerializedName(EthTokenBalanceResponse.ADDRESS_KEY)
    val address: String,

    @SerializedName(EthTokenBalanceResponse.BALANCE_KEY)
    val balance: String
)