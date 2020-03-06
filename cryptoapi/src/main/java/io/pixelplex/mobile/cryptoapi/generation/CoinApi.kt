package io.pixelplex.mobile.cryptoapi.generation

import io.pixelplex.mobile.cryptoapi.annotation.CallbackError
import io.pixelplex.mobile.cryptoapi.annotation.CallbackSuccess
import io.pixelplex.mobile.cryptoapi.annotation.Get
import io.pixelplex.mobile.cryptoapi.model.exception.ApiException

interface CoinApi {

    @Get("coins")
    fun getCoins(
        @CallbackSuccess onSuccess: (List<String>) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )
}