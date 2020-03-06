package io.pixelplex.mobile.cryptoapi.model.data.eth

import com.google.gson.annotations.SerializedName

/**
 * Combines all fields of ETH balance
 *
 * @author Sergey Krupenich
 */
data class EthBalance (
    @SerializedName("address")
    val address: String,

    @SerializedName("balance")
    val balance: String
)