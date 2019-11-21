package io.pixelplex.model.response

import com.google.gson.annotations.SerializedName

/**
 * Implementation of [CryptoApiResponse]
 * Combines some specific properties of ETH info response
 *
 * @author Sergey Krupenich
 */
data class EthInfoResponse(
    val info: List<EthInfo>
) : CryptoApiResponse {
    companion object {
        const val ADDRESS_KEY = "address"
        const val BALANCE_KEY = "balance"
        const val IS_CONTRACT_KEY = "is_contract"
        const val TYPE_KEY = "type"
        const val COUNT_TRANSACTIONS_KEY = "count_transactions"
    }
}

/**
 * Combines all fields of ETH info
 *
 * @author Sergey Krupenich
 */
data class EthInfo(
    @SerializedName(EthInfoResponse.ADDRESS_KEY)
    val address: String,

    @SerializedName(EthInfoResponse.BALANCE_KEY)
    val balance: String,

    @SerializedName(EthInfoResponse.IS_CONTRACT_KEY)
    val isContract: Boolean,

    @SerializedName(EthInfoResponse.TYPE_KEY)
    val type: String,

    @SerializedName(EthInfoResponse.COUNT_TRANSACTIONS_KEY)
    val countTransactions: Long
)