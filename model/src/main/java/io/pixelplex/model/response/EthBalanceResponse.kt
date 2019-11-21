package io.pixelplex.model.response

import com.google.gson.annotations.SerializedName

data class EthBalanceResponse (
    val balances: List<EthBalance>
): CryptoApiResponse {
    companion object {
        const val ADDRESS_KEY = "address"
        const val BALANCE_KEY = "balance"
    }
}

data class EthBalance (
    @SerializedName(EthBalanceResponse.ADDRESS_KEY)
    val address: String,

    @SerializedName(EthBalanceResponse.BALANCE_KEY)
    val balance: String
)