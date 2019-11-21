package io.pixelplex.cryptoapi_android_framework.generation

import io.pixelplex.annotation.*
import io.pixelplex.model.data.EthEstimatedGasCallBody

import io.pixelplex.model.response.EthEstimatedGasResponse
import io.pixelplex.model.response.EthBalanceResponse
import io.pixelplex.model.response.EthNetworkResponse
import io.pixelplex.model.response.EthTransferResponse

@Coin("eth")
interface EthAsyncApi {

    @Get("network")
    suspend fun getNetwork(): EthNetworkResponse

    @Get("accounts/{typedParams}/balance")
    suspend fun getBalances(
        @Path("addresses") addresses: String
    ): EthBalanceResponse

    @Get("tokens/{token}/{typedParams}/transfers/")
    suspend fun getTransfers(
        @Path("token") token: String,
        @Path("addresses") addresses: String
    ): EthTransferResponse

    @Post("estimate-gas")
    suspend fun estimateGas(
        @Body ethEstimatedGasCall: EthEstimatedGasCallBody
    ): EthEstimatedGasResponse
}