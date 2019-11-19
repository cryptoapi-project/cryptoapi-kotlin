package io.pixelplex.cryptoapi_android_framework.core.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EthInfoResponse(
    val info: List<EthInfo>?,

    @SerializedName(ERRORS_KEY)
    val errors: List<ErrorResponse>? = null,

    @SerializedName(STATUS_KEY)
    val status: Int? = null
) : CryptoApiResponse {
    companion object {
        const val ADDRESS_KEY = "address"
        const val BALANCE_KEY = "balance"
        const val IS_CONTRACT_KEY = "is_contract"
        const val TYPE_KEY = "type"
        const val COUNT_TRANSACTIONS_KEY = "count_transactions"
        const val ERRORS_KEY = "errors"
        const val STATUS_KEY = "status"
    }
}

data class EthInfo (
    @SerializedName(EthInfoResponse.ADDRESS_KEY) @Expose
    val address: String,

    @SerializedName(EthInfoResponse.BALANCE_KEY) @Expose
    val balance: String,

    @SerializedName(EthInfoResponse.IS_CONTRACT_KEY) @Expose
    val isContract: Boolean,

    @SerializedName(EthInfoResponse.TYPE_KEY) @Expose
    val type: String,

    @SerializedName(EthInfoResponse.COUNT_TRANSACTIONS_KEY) @Expose
    val countTransactions: Long
)