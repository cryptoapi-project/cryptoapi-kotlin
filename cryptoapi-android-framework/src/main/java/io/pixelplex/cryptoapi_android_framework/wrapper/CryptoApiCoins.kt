package io.pixelplex.cryptoapi_android_framework.wrapper

import io.pixelplex.cryptoapi_android_framework.core.model.response.CoinsResponse
import java.io.IOException

interface CryptoApiCoins {
    fun getCoins(onSuccess: (CoinsResponse) -> Unit, onError: (IOException) -> Unit)
}