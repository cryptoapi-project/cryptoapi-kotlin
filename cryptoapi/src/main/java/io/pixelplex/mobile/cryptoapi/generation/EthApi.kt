package io.pixelplex.mobile.cryptoapi.generation

import io.pixelplex.mobile.cryptoapi.annotation.*
import io.pixelplex.mobile.cryptoapi.model.data.eth.*
import io.pixelplex.mobile.cryptoapi.model.data.push.FirebaseToken
import io.pixelplex.mobile.cryptoapi.model.data.push.NotificationResponse
import io.pixelplex.mobile.cryptoapi.model.exception.ApiException

@Coin("eth")
interface EthApi {

    companion object {
        private const val DEFAULT_PAGE_SIZE = 20
    }

    @Get("network")
    fun getNetwork(
        @CallbackSuccess onSuccess: (EthNetwork) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("addresses/{addresses}/balance")
    fun getBalances(
        @Path("addresses") addresses: List<String>,
        @CallbackSuccess onSuccess: (List<EthBalance>) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("addresses/{addresses}/transfers/")
    fun getTransfers(
        @Path("addresses") addresses: List<String>,
        @Query("positive") positive: Boolean,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE,
        @CallbackSuccess onSuccess: (EthTransfer) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Post("estimate-gas")
    fun estimateGas(
        @Body estimatedGas: EthEstimatedGasCall,
        @CallbackSuccess onSuccess: (EthEstimatedGas) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Post("contracts/{address}/call")
    fun callContract(
        @Path("address") address: String,
        @Body body: EthContractCall,
        @CallbackSuccess onSuccess: (String) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Post("transactions/raw/send")
    fun sendRawTransaction(
        @Body body: EthTransactionRawCall,
        @CallbackSuccess onSuccess: (String) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Post("transactions/raw/decode")
    fun decodeRawTransaction(
        @Body body: EthTransactionRawCall,
        @CallbackSuccess onSuccess: (EthTransactionRawDecodeResponse) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("addresses/{addresses}/transactions")
    fun getExternalTransactions(
        @Path("addresses") addresses: List<String>,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE,
        @CallbackSuccess onSuccess: (EthTransactionExternal) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("addresses/{addresses}")
    fun getInfo(
        @Path("addresses") addresses: List<String>,
        @CallbackSuccess onSuccess: (List<EthInfo>) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("transactions")
    fun getTransactions(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE,
        @CallbackSuccess onSuccess: (EthTransactions) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("transactions/{hash}")
    fun getTransaction(
        @Path("hash") hsh: String,
        @CallbackSuccess onSuccess: (EthTransaction) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("transactions/receipt/{hash}")
    fun getTransactionReceipt(
        @Path("hash") hsh: String,
        @CallbackSuccess onSuccess: (Receipt) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("contracts/{address}")
    fun getContractInfo(
        @Path("address") address: String,
        @CallbackSuccess onSuccess: (EthContractBytecodeResponse) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("contracts/logs")
    fun getContractLogs(
        @Query("from_block") from: String,
        @Query("to_block") to: String,
        @Query("addresses") addresses: List<String>,
        @Query("topics") topics: List<String>,
        @CallbackSuccess onSuccess: (List<Log>) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("addresses/{addresses}/balance/tokens")
    fun getTokenBalances(
        @Path("addresses") addresses: List<String>,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE,
        @CallbackSuccess onSuccess: (EthTokenBalance) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("addresses/{addresses}/balance/tokens/{token}")
    fun getTokenBalances(
        @Path("addresses") addresses: List<String>,
        @Path("token") token: String,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE,
        @CallbackSuccess onSuccess: (EthTokenBalance) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("addresses/{addresses}/transfers/tokens/{token}")
    fun getTokenTransfers(
        @Path("token") token: String,
        @Path("addresses") addresses: List<String>,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE,
        @CallbackSuccess onSuccess: (EthTokenTransfer) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("tokens/{token}/transfers")
    fun getTokenTransfers(
        @Path("token") token: String,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE,
        @CallbackSuccess onSuccess: (EthTokenTransfer) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("tokens/{address}")
    fun getTokenInfo(
        @Path("address") address: String,
        @CallbackSuccess onSuccess: (EthTokenInfo) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("tokens/search")
    fun searchTokens(
        @Query("query") query: String,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE,
        @Query("types") types: List<String>,
        @CallbackSuccess onSuccess: (EthTokenSearch) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Post("push-notifications/addresses/{addresses}")
    fun subscribeNotifications(
        @Path("addresses") addresses: List<String>,
        @Body body: FirebaseToken,
        @CallbackSuccess onSuccess: (NotificationResponse) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Delete("push-notifications/addresses/{addresses}")
    fun unsubscribeNotifications(
        @Path("addresses") addresses: List<String>,
        @Query("firebase_token") token: String,
        @Query("types") types: List<String>,
        @CallbackSuccess onSuccess: (NotificationResponse) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )
}