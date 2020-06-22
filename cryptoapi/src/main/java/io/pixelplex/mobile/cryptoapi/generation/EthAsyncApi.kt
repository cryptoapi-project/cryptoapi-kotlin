package io.pixelplex.mobile.cryptoapi.generation

import io.pixelplex.mobile.cryptoapi.annotation.*
import io.pixelplex.mobile.cryptoapi.model.data.eth.*
import io.pixelplex.mobile.cryptoapi.model.data.push.FirebaseToken
import io.pixelplex.mobile.cryptoapi.model.data.push.NotificationResponse
import io.pixelplex.mobile.cryptoapi.model.data.push.NotificationType

@Coin("eth")
interface EthAsyncApi {

    companion object {
        private const val DEFAULT_PAGE_SIZE = 20
    }

    @Get("network")
    suspend fun getNetwork(): EthNetwork

    @Get("addresses/{addresses}/balance")
    suspend fun getBalances(
        @Path("addresses") addresses: List<String>
    ): List<EthBalance>

    @Get("addresses/{addresses}/transfers/")
    suspend fun getTransfers(
        @Path("addresses") addresses: List<String>,
        @Query("positive") positive: Boolean,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE
    ): EthTransfer

    @Post("estimate-gas")
    suspend fun estimateGas(
        @Body estimatedGas: EthEstimatedGasCall
    ): EthEstimatedGas

    @Post("contracts/{address}/call")
    suspend fun callContract(
        @Path("address") address: String,
        @Body body: EthContractCall
    ): String

    @Post("transactions/raw/send")
    suspend fun sendRawTransaction(
        @Body body: EthTransactionRawCall
    ): String

    @Post("transactions/raw/decode")
    suspend fun decodeRawTransaction(
        @Body body: EthTransactionRawCall
    ): EthTransactionRawDecodeResponse

    @Get("addresses/{addresses}/transactions")
    suspend fun getExternalTransactions(
        @Path("addresses") addresses: List<String>,
        @Query("pending") pending: PendingType = PendingType.INCLUDE,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE
    ): EthTransactionExternal

    @Get("addresses/{addresses}")
    suspend fun getInfo(
        @Path("addresses") addresses: List<String>
    ): List<EthInfo>

    @Get("transactions")
    suspend fun getTransactions(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE
    ): EthTransactions

    @Get("transactions/{hash}")
    suspend fun getTransaction(
        @Path("hash") hsh: String
    ): EthTransaction

    @Get("transactions/receipt/{hash}")
    suspend fun getTransactionReceipt(
        @Path("hash") hsh: String
    ): Receipt

    @Get("contracts/{address}")
    suspend fun getContractInfo(
        @Path("address") address: String
    ): EthContractBytecodeResponse

    @Get("contracts/logs")
    suspend fun getContractLogs(
        @Query("from_block") from: String,
        @Query("to_block") to: String,
        @Query("addresses") addresses: List<String>,
        @Query("topics") topics: List<String>
    ): List<Log>

    @Get("addresses/{addresses}/balance/tokens")
    suspend fun getTokenBalances(
        @Path("addresses") addresses: List<String>,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE
    ): EthTokenBalance

    @Get("addresses/{addresses}/balance/tokens/{token}")
    suspend fun getTokenBalances(
        @Path("addresses") addresses: List<String>,
        @Path("token") token: String,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE
    ): EthTokenBalance

    @Get("addresses/{addresses}/transfers/tokens/{token}")
    suspend fun getTokenTransfers(
        @Path("token") token: String,
        @Path("addresses") addresses: List<String>,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE
    ): EthTokenTransfer

    @Get("tokens/{token}/transfers")
    suspend fun getTokenTransfers(
        @Path("token") token: String,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE
    ): EthTokenTransfer

    @Get("tokens/{address}")
    suspend fun getTokenInfo(
        @Path("address") address: String
    ): EthTokenInfo

    @Get("tokens/search")
    suspend fun searchTokens(
        @Query("query") query: String,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE,
        @Query("types") types: List<String>
    ): EthTokenSearch

    @Post("push-notifications/addresses/{addresses}")
    suspend fun subscribeNotifications(
        @Path("addresses") addresses: List<String>,
        @Body body: FirebaseToken
    ): NotificationResponse

    @Delete("push-notifications/addresses/{addresses}")
    suspend fun unsubscribeNotifications(
        @Path("addresses") addresses: List<String>,
        @Query("firebase_token") token: String,
        @Query("types") types: List<String>
    ): NotificationResponse
}