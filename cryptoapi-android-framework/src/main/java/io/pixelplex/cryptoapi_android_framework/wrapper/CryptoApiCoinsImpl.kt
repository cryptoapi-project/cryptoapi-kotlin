package io.pixelplex.cryptoapi_android_framework.wrapper

import io.pixelplex.cryptoapi_android_framework.core.CryptoApi
import io.pixelplex.cryptoapi_android_framework.core.model.response.CoinsResponse
import io.pixelplex.cryptoapi_android_framework.support.fromJson
import java.io.IOException

class CryptoApiCoinsImpl(
    private val crptoApiClient: CryptoApi
): CryptoApiCoins {
    override fun getCoins(onSuccess: (CoinsResponse) -> Unit, onError: (IOException) -> Unit) {
        crptoApiClient.callApi(
            COINS_PARAM,
            { responseJson ->
                onSuccess(
                    CoinsResponse(
                        fromJson(responseJson)
                    )
                )
            },
            onError
        )
    }

    companion object {
        private const val COINS_PARAM = "coins"
    }
}