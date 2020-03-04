package io.pixelplex.mobile.cryptoapi.model.data.eth

import com.google.gson.annotations.SerializedName
import io.pixelplex.mobile.cryptoapi.model.common.CryptoApiResponse

/**
 * Implementation of [CryptoApiResponse]
 * Combines some specific properties of ETH token info response
 *
 * @author Sergey Krupenich
 */
data class EthTokenInfo(
    @SerializedName("address")
    val address: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("create_transaction_hash")
    val createTransactionHash: String,

    @SerializedName("holders_count")
    val holdersCount: Int,

    @SerializedName("info")
    val info: EthTokenSearchInfo

) : CryptoApiResponse

