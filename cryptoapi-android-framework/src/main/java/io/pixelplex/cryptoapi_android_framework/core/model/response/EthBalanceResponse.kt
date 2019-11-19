package io.pixelplex.cryptoapi_android_framework.core.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EthBalanceResponse (
    val balances: List<EthBalance>?,

    @SerializedName(ERRORS_KEY)
    val errors: List<ErrorResponse>? = null,

    @SerializedName(STATUS_KEY)
    val status: Int? = null
): CryptoApiResponse {
    companion object {
        const val ADDRESS_KEY = "address"
        const val BALANCE_KEY = "balance"
        const val ERRORS_KEY = "errors"
        const val STATUS_KEY = "status"
    }
}

data class EthBalance (
    @SerializedName(EthBalanceResponse.ADDRESS_KEY) @Expose
    val address: String,

    @SerializedName(EthBalanceResponse.BALANCE_KEY) @Expose
    val balance: String
)