package io.pixelplex.mobile.cryptoapi.generation

import io.pixelplex.mobile.cryptoapi.annotation.Get
import io.pixelplex.mobile.cryptoapi.annotation.Path
import io.pixelplex.mobile.cryptoapi.model.common.HistoryRateModel
import io.pixelplex.mobile.cryptoapi.model.common.RateModel

interface RatesAsyncApi {
    @Get("rates/{coins}")
    suspend fun getRates(
        @Path("coins") coins: List<String>
    ): List<RateModel>

    @Get("rates/{coins}/history")
    suspend fun getRatesHistory(
        @Path("coins") coins: List<String>
    ): List<HistoryRateModel>
}