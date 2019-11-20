package io.pixelplex.model.response

import com.google.gson.annotations.SerializedName

data class EthTokensBalancesResponse (
    @SerializedName(ITEMS_KEY)
    val items: List<EthTokensBalancesItem>,

    @SerializedName(TOTAL_KEY)
    val total: Int,

    @SerializedName(ERRORS_KEY)
    val errors: List<ErrorResponse>? = null,

    @SerializedName(STATUS_KEY)
    val status: Int? = null
): CryptoApiResponse {
    companion object {
        const val TOTAL_KEY = "total"
        const val ADDRESS_KEY = "address"
        const val BALANCE_KEY = "balance"
        const val ITEMS_KEY = "items"
        const val ERRORS_KEY = "errors"
        const val STATUS_KEY = "status"
    }
}

data class EthTokensBalancesItem (
    @SerializedName(EthTokensBalancesResponse.ADDRESS_KEY)
    val address: String,

    @SerializedName(EthTokensBalancesResponse.BALANCE_KEY)
    val balance: String
)