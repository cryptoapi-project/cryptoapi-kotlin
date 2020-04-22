package io.pixelplex.mobile.cryptoapi.generation

import io.pixelplex.mobile.cryptoapi.annotation.CallbackError
import io.pixelplex.mobile.cryptoapi.annotation.CallbackSuccess
import io.pixelplex.mobile.cryptoapi.annotation.Get
import io.pixelplex.mobile.cryptoapi.annotation.Path
import io.pixelplex.mobile.cryptoapi.model.common.HistoryRateModel
import io.pixelplex.mobile.cryptoapi.model.common.RateModel
import io.pixelplex.mobile.cryptoapi.model.exception.ApiException

interface RatesApi {
    @Get("rates/{coins}")
    fun getRates(
        @Path("coins") coins: List<String>,
        @CallbackSuccess onSuccess: (List<RateModel>) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("rates/{coins}/history")
    fun getRatesHistory(
        @Path("coins") coins: List<String>,
        @CallbackSuccess onSuccess: (List<HistoryRateModel>) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )
}