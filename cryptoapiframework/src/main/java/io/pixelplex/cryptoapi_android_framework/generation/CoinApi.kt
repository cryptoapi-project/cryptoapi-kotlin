package io.pixelplex.cryptoapi_android_framework.generation

import io.pixelplex.annotation.CallbackError
import io.pixelplex.annotation.CallbackSuccess
import io.pixelplex.annotation.Get
import io.pixelplex.model.exception.ApiException

interface CoinApi {

    @Get("coins")
    fun getCoins(
        @CallbackSuccess onSuccess: (List<String>) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )
}