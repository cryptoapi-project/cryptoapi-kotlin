package io.pixelplex.model.response

import com.google.gson.annotations.SerializedName

/**
 * Implementation of [CryptoApiResponse]
 * Combines some specific properties of ETH raw transaction response
 *
 * @author Sergey Krupenich
 */
data class EthTransactionRawResponse(
    @SerializedName(HASH_KEY)
    val hash: String? = null
) : CryptoApiResponse {
    companion object {
        const val HASH_KEY = "hash"
    }
}

/**
 * Implementation of [CryptoApiResponse]
 * Combines some specific properties of ETH raw transaction decoded response
 *
 * @author Sergey Krupenich
 */
data class EthTransactionRawDecodeResponse(
    @SerializedName(NONCE_KEY)
    val nonce: Long? = null,

    @SerializedName(GAS_PRICE_KEY)
    val gasPrice: EthHex? = null,

    @SerializedName(GAS_LIMIT_KEY)
    val gasLimit: EthHex? = null,

    @SerializedName(TO_KEY)
    val to: String? = null,

    @SerializedName(VALUE_KEY)
    val value: EthHex? = null,

    @SerializedName(DATA_KEY)
    val data: String? = null,

    @SerializedName(V_KEY)
    val v: Int? = null,

    @SerializedName(R_KEY)
    val r: String? = null,

    @SerializedName(S_KEY)
    val s: String? = null
) : CryptoApiResponse {
    companion object {
        const val NONCE_KEY = "nonce"
        const val GAS_PRICE_KEY = "gasPrice"
        const val GAS_LIMIT_KEY = "gasLimit"
        const val TO_KEY = "to"
        const val VALUE_KEY = "value"
        const val DATA_KEY = "data"
        const val V_KEY = "v"
        const val R_KEY = "r"
        const val S_KEY = "s"
        const val HEX_KEY = "_hex"
    }
}

/**
 * Combines all fields of ETH Hex
 *
 * @author Sergey Krupenich
 */
data class EthHex(
    @SerializedName(EthTransactionRawDecodeResponse.HEX_KEY)
    val hex: String
)