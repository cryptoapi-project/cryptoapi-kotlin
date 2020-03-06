package io.pixelplex.mobile.cryptoapi.model.data.eth

import com.google.gson.annotations.SerializedName
import io.pixelplex.mobile.cryptoapi.model.common.CryptoApiResponse

/**
 * Implementation of [CryptoApiResponse]
 * Combines some specific properties of ETH token balance response
 *
 * @author Sergey Krupenich
 */
data class EthTokenBalance(
    @SerializedName("items")
    val items: List<EthTokensAddressWithBalance>,

    @SerializedName("total")
    val total: Int
) : CryptoApiResponse

/**
 * Implementation of [CryptoApiResponse]
 * Combines all fields ETH token balance
 *
 * @author Sergey Krupenich
 */
data class EthTokensAddressWithBalance(
    @SerializedName("address")
    val address: String,

    @SerializedName("balance")
    val balance: String,

    @SerializedName("holder")
    val holder: String
)