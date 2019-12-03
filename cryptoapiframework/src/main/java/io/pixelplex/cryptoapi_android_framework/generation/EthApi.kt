package io.pixelplex.cryptoapi_android_framework.generation

import io.pixelplex.annotation.*
import io.pixelplex.annotation.Coin
import io.pixelplex.model.data.EthContractCallBody
import io.pixelplex.model.data.EthEstimatedGasCallBody
import io.pixelplex.model.data.EthTransactionRawCallBody
import io.pixelplex.model.exception.ApiException
import io.pixelplex.model.response.*

@Coin("eth")
interface EthApi {

    companion object {
        private const val DEFAULT_PAGE_SIZE = 20
    }

    @Get("network")
    fun getNetwork(
        @CallbackSuccess onSuccess: (EthNetworkResponse) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("accounts/{addresses}/balance")
    fun getBalances(
        @Path("addresses") addresses: List<String>,
        @CallbackSuccess onSuccess: (List<EthBalance>) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("tokens/{token}/{addresses}/transfers/")
    fun getTransfers(
        @Path("token") token: String,
        @Path("addresses") addresses: List<String>,
        @CallbackSuccess onSuccess: (EthTransferResponse) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Post("estimate-gas")
    fun estimateGas(
        @Body ethEstimatedGasCall: EthEstimatedGasCallBody,
        @CallbackSuccess onSuccess: (EthEstimatedGasResponse) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Post("contracts/{address}/call")
    fun callContract(
        @Path("address") address: String,
        @Body body: EthContractCallBody,
        @CallbackSuccess onSuccess: (String) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Post("transactions/raw/send")
    fun sendRawTransaction(
        @Body body: EthTransactionRawCallBody,
        @CallbackSuccess onSuccess: (String) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Post("transactions/raw/decode")
    fun decodeRawTransaction(
        @Body body: EthTransactionRawCallBody,
        @CallbackSuccess onSuccess: (EthTransactionRawDecodeResponse) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("accounts/{addresses}/transactions/external")
    fun getExternalTransactions(
        @Path("addresses") addresses: List<String>,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE,
        @CallbackSuccess onSuccess: (EthTransactionExternalResponse) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("accounts/{addresses}/info")
    fun getInfo(
        @Path("addresses") addresses: List<String>,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE,
        @CallbackSuccess onSuccess: (List<EthInfo>) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("transactions")
    fun getTransactions(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE,
        @CallbackSuccess onSuccess: (EthTransactionsResponse) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("transactions/{hash}")
    fun getTransaction(
        @Path("hash") hsh: String,
        @CallbackSuccess onSuccess: (EthTransactionResponse) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("contracts/{address}/info")
    fun getContractInfo(
        @Path("address") address: String,
        @CallbackSuccess onSuccess: (EthContractBytecodeResponse) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("tokens/{address}/balances")
    fun getTokenBalances(
        @Path("address") address: String,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE,
        @CallbackSuccess onSuccess: (EthTokenBalanceResponse) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("tokens/{token}/{addresses}/transfers")
    fun getTokenTransfers(
        @Path("token") token: String,
        @Path("addresses") addresses: List<String>,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE,
        @CallbackSuccess onSuccess: (EthTokenTransferResponse) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("tokens/{address}/info")
    fun getTokenInfo(
        @Path("address") address: String,
        @CallbackSuccess onSuccess: (EthTokenInfoResponse) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("tokens/search")
    fun searchTokens(
        @Query("query") query: String,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE,
        @Query("types") types: List<String>,
        @CallbackSuccess onSuccess: (EthTokenSearchResponse) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )
}