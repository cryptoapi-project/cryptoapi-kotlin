package io.pixelplex.model.response

import com.google.gson.annotations.SerializedName

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

data class EthTokensBalance (
    @SerializedName(EthTokenBalanceResponse.ADDRESS_KEY)
    val address: String,

    @SerializedName(EthTokenBalanceResponse.BALANCE_KEY)
    val balance: String
)