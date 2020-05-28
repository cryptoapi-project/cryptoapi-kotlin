package io.pixelplex.mobile.cryptoapi.model.data.btc

import com.google.gson.annotations.SerializedName
import io.pixelplex.mobile.cryptoapi.model.common.CryptoApiResponse
import java.math.BigDecimal
import java.math.BigInteger

/**
 * Implementation of [CryptoApiResponse]
 * Combines some specific properties of ETH network response
 *
 * @author Sergey Krupenich
 */
data class BtcNetwork(
    @SerializedName(LAST_BLOCK_KEY)
    val lastBlock: BigInteger,

    @SerializedName(COUNT_TRANSACTIONS_KEY)
    val countTransactions: String,

    @SerializedName(HASH_RATE_KEY)
    val hashRate: BigDecimal,

    @SerializedName(DIFFICULTY_KEY)
    val difficulty: BigDecimal,

    @SerializedName(ESTIMATE_FEE_KEY)
    val estimateFee: Double
) : CryptoApiResponse {
    companion object {
        const val LAST_BLOCK_KEY = "last_block"
        const val COUNT_TRANSACTIONS_KEY = "count_transactions"
        const val HASH_RATE_KEY = "hashrate"
        const val DIFFICULTY_KEY = "difficulty"
        const val ESTIMATE_FEE_KEY = "estimate_fee"
    }
}