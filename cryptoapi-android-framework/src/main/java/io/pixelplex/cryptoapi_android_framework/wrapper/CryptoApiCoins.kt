package io.pixelplex.cryptoapi_android_framework.wrapper

import io.pixelplex.model.response.CoinsResponse
import io.pixelplex.model.response.ErrorResponse

interface CryptoApiCoins {
    fun getCoins(onSuccess: (CoinsResponse) -> Unit, onError: (ErrorResponse) -> Unit)
}