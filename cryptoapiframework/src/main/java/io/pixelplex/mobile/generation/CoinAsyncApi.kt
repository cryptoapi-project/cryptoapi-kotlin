package io.pixelplex.mobile.generation

import io.pixelplex.annotation.Get

interface CoinAsyncApi {

    @Get("coins")
    suspend fun getCoins(): List<String>
}