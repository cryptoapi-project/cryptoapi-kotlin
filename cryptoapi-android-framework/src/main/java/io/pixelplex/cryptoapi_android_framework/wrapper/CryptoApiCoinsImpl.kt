package io.pixelplex.cryptoapi_android_framework.wrapper

import io.pixelplex.cryptoapi_android_framework.core.CryptoApi
import io.pixelplex.cryptoapi_android_framework.core.CryptoApi.RequestMethod.GET
import io.pixelplex.cryptoapi_android_framework.support.fromJson
import io.pixelplex.model.response.CoinsResponse
import io.pixelplex.model.response.ErrorResponse

class CryptoApiCoinsImpl(
    private val cryptoApiClient: CryptoApi
) : CryptoApiCoins {
    override fun getCoins(onSuccess: (CoinsResponse) -> Unit, onError: (ErrorResponse) -> Unit) {
        cryptoApiClient.callApi(
            params = COINS_PARAM,
            method = GET,
            onSuccess = { responseJson ->
                onSuccess(
                    CoinsResponse(
                        fromJson(responseJson)
                    )
                )
            },
            onError = { responseJson -> onError(fromJson(responseJson)) }
        )
    }

    companion object {
        private const val COINS_PARAM = "coins"
    }
}