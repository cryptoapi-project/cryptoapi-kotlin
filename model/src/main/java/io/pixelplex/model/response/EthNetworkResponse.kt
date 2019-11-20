package io.pixelplex.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EthNetworkResponse (
    @SerializedName(LAST_BLOCK_KEY)
    val lastBlock: Long,

    @SerializedName(COUNT_TRANSACTIONS_KEY)
    val countTransactions: String,

    @SerializedName(GAS_PRICE_KEY)
    val gasPrice: Long,

    @SerializedName(HASH_RATE_KEY)
    val hashRate: Long,

    @SerializedName(DIFFICULTY_KEY)
    val difficulty: Long
): CryptoApiResponse {
    companion object {
        const val LAST_BLOCK_KEY = "lastBlock"
        const val COUNT_TRANSACTIONS_KEY = "countTransactions"
        const val GAS_PRICE_KEY = "gasPrice"
        const val HASH_RATE_KEY = "hashRate"
        const val DIFFICULTY_KEY = "difficulty"
    }
}