package io.pixelplex.cryptoapi_android_framework

import io.pixelplex.annotation.COIN
import io.pixelplex.annotation.GET
import io.pixelplex.cryptoapi_android_framework.core.model.response.CoinsResponse
import io.pixelplex.tools.TypedCallback

@COIN("eth")
interface EthApi {

    @GET("network")
    fun getNetwork(
        callback: TypedCallback<CoinsResponse>
    ): Any

    @GET("accounts/{addresses}/balance")
    fun getNetwork(
        vararg params: String,
        callback: TypedCallback<CoinsResponse>
    ): Any

}