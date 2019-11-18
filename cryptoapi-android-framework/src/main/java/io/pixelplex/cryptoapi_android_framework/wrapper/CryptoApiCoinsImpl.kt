package io.pixelplex.cryptoapi_android_framework.wrapper

import io.pixelplex.cryptoapi_android_framework.core.CryptoApi
import io.pixelplex.cryptoapi_android_framework.core.model.response.CoinsResponse
import java.io.IOException

class CryptoApiCoinsImpl(
    private val crptoApiClient: CryptoApi
): CryptoApiCoins {
    override fun getCoins(onSuccess: (CoinsResponse) -> Unit, onError: (IOException) -> Unit) {
        crptoApiClient.call<CoinsResponse>(
            COINS_PARAM,
            onSuccess,
            onError
        )
    }

    companion object {
        private const val COINS_PARAM = "coins"
    }
}