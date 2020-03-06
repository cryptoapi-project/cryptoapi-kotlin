package io.pixelplex.mobile.cryptoapi.model.data.eth

import com.google.gson.annotations.SerializedName

/**
 * Combines all fields of ETH info
 *
 * @author Sergey Krupenich
 */
data class EthInfo(
    @SerializedName("address")
    val address: String,

    @SerializedName("balance")
    val balance: String,

    @SerializedName("is_contract")
    val isContract: Boolean,

    @SerializedName("type")
    val type: String,

    @SerializedName("count_transactions")
    val countTransactions: Long
)