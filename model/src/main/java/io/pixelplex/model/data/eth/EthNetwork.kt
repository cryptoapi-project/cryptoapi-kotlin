package io.pixelplex.model.data.eth

import com.google.gson.annotations.SerializedName
import io.pixelplex.model.common.CryptoApiResponse

/**
 * Implementation of [CryptoApiResponse]
 * Combines some specific properties of ETH network response
 *
 * @author Sergey Krupenich
 */
data class EthNetwork(
    @SerializedName("last_block")
    val lastBlock: Long,

    @SerializedName("count_transactions")
    val countTransactions: String,

    @SerializedName("gas_price")
    val gasPrice: Long,

    @SerializedName("hashrate")
    val hashRate: Long,

    @SerializedName("difficulty")
    val difficulty: Long
) : CryptoApiResponse