package io.pixelplex.mobile.cryptoapi.generation

import io.pixelplex.mobile.cryptoapi.annotation.Get

interface CoinAsyncApi {

    @Get("coins")
    suspend fun getCoins(): List<String>
}