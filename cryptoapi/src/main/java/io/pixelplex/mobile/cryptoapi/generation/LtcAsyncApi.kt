package io.pixelplex.mobile.cryptoapi.generation

import io.pixelplex.mobile.cryptoapi.annotation.*
import io.pixelplex.mobile.cryptoapi.model.common.ApiResult
import io.pixelplex.mobile.cryptoapi.model.data.btc.*
import io.pixelplex.mobile.cryptoapi.model.data.push.FirebaseToken
import io.pixelplex.mobile.cryptoapi.model.data.push.NotificationResponse

@Coin("ltc")
interface LtcAsyncApi {
    companion object {
        private const val MAX_PAGE_SIZE = 15
    }

    @Get("network")
    suspend fun getNetwork(): BtcNetwork

    @Get("blocks/{block_height_or_hash}")
    suspend fun getBlock(
        @Path("block_height_or_hash") blockHeightOrHash: String
    ): BtcBlock

    @Get("blocks")
    suspend fun getBlocks(
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = MAX_PAGE_SIZE
    ): BtcBlocks

    @Get("transactions/{hash}")
    suspend fun getTransaction(
        @Path("hash") transactionHash: String
    ): BtcTransaction

    @Get("transactions")
    suspend fun getTransactions(
        @Query("block_height_or_hash") blockHeightOrHash: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = MAX_PAGE_SIZE
    ): BtcTransactions

    @Get("addresses/{addresses}/transactions")
    suspend fun getTransactions(
        @Path("addresses") addresses: List<String>,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = MAX_PAGE_SIZE
    ): BtcTransactions

    @Post("transactions/raw/send")
    suspend fun sendRawTransaction(
        @Body body: BtcRawTransaction
    ): ApiResult<String>

    @Post("transactions/raw/decode")
    suspend fun decodeRawTransaction(
        @Body body: BtcRawTransaction
    ): BtcDecodedRawTransaction

    @Get("addresses/{addresses}/outputs")
    suspend fun getOutputs(
        @Query("status") status: BtcOutputStatus,
        @Path("addresses") addresses: List<String>,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = MAX_PAGE_SIZE
    ): List<BtcOutput>

    @Get("addresses/{addresses}")
    suspend fun getAddressesWithBalances(
        @Path("addresses") addresses: List<String>
    ): List<AddressWithBalance>

    @Get("estimate-fee")
    suspend fun estimateFee(): String

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