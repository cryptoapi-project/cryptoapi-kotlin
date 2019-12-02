package io.pixelplex.cryptoapi_android_framework.generation

import io.pixelplex.annotation.Get

interface CoinAsyncApi {

    @Get("coins")
    suspend fun getCoins(): List<String>
}