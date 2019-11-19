package io.pixelplex.cryptoapi_android_framework

import io.pixelplex.annotation.COIN
import io.pixelplex.annotation.GET
import io.pixelplex.annotation.PATH
import io.pixelplex.cryptoapi_android_framework.core.model.response.CoinsResponse
import io.pixelplex.cryptoapi_android_framework.core.model.response.EthBalanceResponse
import io.pixelplex.cryptoapi_android_framework.core.model.response.EthNetworkResponse
import io.pixelplex.tools.TypedCallback

@COIN("eth")
interface EthApi {

    @GET("network")
    fun getNetwork(
        callback: TypedCallback<EthNetworkResponse>
    )

    @GET("accounts/{addresses}/balance")
    fun getBalances(
        @PATH("addresses") addresses: String,
        callback: TypedCallback<EthBalanceResponse>
    )

    @GET("tokens/{token}/{addresses}/transfers/")
    fun getTransfers(
        @PATH("token") token: String,
        @PATH("addresses") addresses: String,
        callback: TypedCallback<Any>
    )
}