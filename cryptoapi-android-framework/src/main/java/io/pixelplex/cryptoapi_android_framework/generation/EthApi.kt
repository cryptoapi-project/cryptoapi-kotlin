package io.pixelplex.cryptoapi_android_framework.generation

import io.pixelplex.annotation.*
import io.pixelplex.model.data.EstimatedGasBody

import io.pixelplex.model.response.EstimatedGasResponse
import io.pixelplex.model.response.EthBalanceResponse
import io.pixelplex.model.response.EthNetworkResponse
import io.pixelplex.model.response.EthTransferResponse
import io.pixelplex.tools.TypedCallback

@Coin("eth")
interface EthApi {

    @Get("network")
    fun getNetwork(
        callback: TypedCallback<EthNetworkResponse>
    )

    @Get("accounts/{addresses}/balance")
    fun getBalances(
        @Path("addresses") addresses: String,
        callback: TypedCallback<EthBalanceResponse>
    )

    @Get("tokens/{token}/{addresses}/transfers/")
    fun getTransfers(
        @Path("token") token: String,
        @Path("addresses") addresses: String,
        callback: TypedCallback<EthTransferResponse>
    )

    @Post("estimate-gas")
    fun estimateGas(
        @Body estimatedGas: EstimatedGasBody,
        callback: TypedCallback<EstimatedGasResponse>
    )
}