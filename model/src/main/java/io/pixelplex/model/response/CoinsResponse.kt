package io.pixelplex.model.response

import com.google.gson.annotations.SerializedName

/**
 * Implementation of [CryptoApiResponse]
 * Segregates models, associated with some ETH responses
 *
 * @author Sergey Krupenich
 */
data class CoinsResponse (
    val coins: List<Coin>
): CryptoApiResponse {
    companion object {
        const val ETH_KEY = "eth"
    }
}

/**
 * Contains all supported coins of Crypto API
 *
 * @author Sergey Krupenich
 */
enum class Coin(coin: String) {
    @SerializedName(CoinsResponse.ETH_KEY)
    ETH(CoinsResponse.ETH_KEY)
}