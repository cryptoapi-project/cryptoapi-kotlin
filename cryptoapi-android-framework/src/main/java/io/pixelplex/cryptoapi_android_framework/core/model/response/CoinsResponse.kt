package io.pixelplex.cryptoapi_android_framework.core.model.response

import com.google.gson.annotations.SerializedName

data class CoinsResponse (
    val coins: List<Coin>
): CryptoApiResponse {
    companion object {
        const val ETH_KEY = "eth"
    }
}

enum class Coin(coin: String) {
    @SerializedName(CoinsResponse.ETH_KEY)
    ETH(CoinsResponse.ETH_KEY)
}