package io.pixelplex.model.response

import com.google.gson.annotations.SerializedName

/**
 * Implementation of [CryptoApiResponse]
 * Combines some specific properties of ETH balance response
 *
 * @author Sergey Krupenich
 */
data class EthBalanceResponse (
    val balances: List<EthBalance>
): CryptoApiResponse {
    companion object {
        const val ADDRESS_KEY = "address"
        const val BALANCE_KEY = "balance"
    }
}

/**
 * Combines all fields of ETH balance
 *
 * @author Sergey Krupenich
 */
data class EthBalance (
    @SerializedName(EthBalanceResponse.ADDRESS_KEY)
    val address: String,

    @SerializedName(EthBalanceResponse.BALANCE_KEY)
    val balance: String
)