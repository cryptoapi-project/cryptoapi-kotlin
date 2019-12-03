package io.pixelplex.cryptoapi_android_framework.generation

import io.pixelplex.annotation.*
import io.pixelplex.annotation.Coin
import io.pixelplex.model.data.EthContractCallBody
import io.pixelplex.model.data.EthEstimatedGasCallBody
import io.pixelplex.model.data.EthTransactionRawCallBody
import io.pixelplex.model.response.*

@Coin("eth")
interface EthAsyncApi {

    companion object {
        private const val DEFAULT_PAGE_SIZE = 20
    }

    @Get("network")
    suspend fun getNetwork(): EthNetworkResponse

    @Get("accounts/{addresses}/balance")
    suspend fun getBalances(
        @Path("addresses") addresses: List<String>
    ): List<EthBalance>

    @Get("accounts/{addresses}/transfers/")
    suspend fun getTransfers(
        @Path("addresses") addresses: List<String>,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE
    ): EthTransferResponse

    @Post("estimate-gas")
    suspend fun estimateGas(
        @Body estimatedGas: EthEstimatedGasCallBody
    ): EthEstimatedGasResponse

    @Post("contracts/{address}/call")
    suspend fun callContract(
        @Path("address") address: String,
        @Body body: EthContractCallBody
    ): String

    @Post("transactions/raw/send")
    suspend fun sendRawTransaction(
        @Body body: EthTransactionRawCallBody
    ): String

    @Post("transactions/raw/decode")
    suspend fun decodeRawTransaction(
        @Body body: EthTransactionRawCallBody
    ): EthTransactionRawDecodeResponse

    @Get("accounts/{addresses}/transactions/external")
    suspend fun getExternalTransactions(
        @Path("addresses") addresses: List<String>,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE
    ): EthTransactionExternalResponse

    @Get("accounts/{addresses}/info")
    suspend fun getInfo(
        @Path("addresses") addresses: List<String>,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE
    ): List<EthInfo>

    @Get("transactions")
    suspend fun getTransactions(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE
    ): EthTransactionsResponse

    @Get("transactions/{hash}")
    suspend fun getTransaction(
        @Path("hash") hsh: String
    ): EthTransactionResponse

    @Get("contracts/{address}/info")
    suspend fun getContractInfo(
        @Path("address") address: String
    ): EthContractBytecodeResponse

    @Get("tokens/{address}/balances")
    suspend fun getTokenBalances(
        @Path("address") address: String,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE
    ): EthTokenBalanceResponse

    @Get("tokens/{token}/{addresses}/transfers")
    suspend fun getTokenTransfers(
        @Path("token") token: String,
        @Path("addresses") addresses: List<String>,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE
    ): EthTokenTransferResponse

    @Get("tokens/{address}/info")
    suspend fun getTokenInfo(
        @Path("address") address: String
    ): EthTokenInfoResponse

    @Get("tokens/search")
    suspend fun searchTokens(
        @Query("query") query: String,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE,
        @Query("types") types: List<String>
    ): EthTokenSearchResponse
}