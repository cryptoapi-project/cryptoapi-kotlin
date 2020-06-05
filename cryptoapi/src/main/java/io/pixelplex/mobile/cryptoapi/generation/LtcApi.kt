package io.pixelplex.mobile.cryptoapi.generation

import io.pixelplex.mobile.cryptoapi.annotation.*
import io.pixelplex.mobile.cryptoapi.model.common.ApiResult
import io.pixelplex.mobile.cryptoapi.model.data.btc.*
import io.pixelplex.mobile.cryptoapi.model.data.push.FirebaseToken
import io.pixelplex.mobile.cryptoapi.model.data.push.NotificationResponse
import io.pixelplex.mobile.cryptoapi.model.exception.ApiException

@Coin("ltc")
interface LtcApi {
    companion object {
        private const val DEFAULT_PAGE_SIZE = 20
    }

    @Get("network")
    fun getNetwork(
        @CallbackSuccess onSuccess: (BtcNetwork) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("blocks/{block_height_or_hash}")
    fun getBlock(
        @Path("block_height_or_hash") blockHeightOrHash: String,
        @CallbackSuccess onSuccess: (BtcBlock) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("blocks")
    fun getBlocks(
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE,
        @CallbackSuccess onSuccess: (BtcBlocks) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("transactions/{hash}")
    fun getTransaction(
        @Path("hash") transactionHash: String,
        @CallbackSuccess onSuccess: (BtcTransaction) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("transactions")
    fun getTransactions(
        @Query("block_height_or_hash") blockHeightOrHash: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE,
        @CallbackSuccess onSuccess: (BtcTransactions) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("addresses/{addresses}/transactions")
    fun getTransactions(
        @Path("addresses") addresses: List<String>,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE,
        @CallbackSuccess onSuccess: (BtcTransactions) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Post("transactions/raw/send")
    fun sendRawTransaction(
        @Body body: BtcRawTransaction,
        @CallbackSuccess onSuccess: (ApiResult<String>) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Post("transactions/raw/decode")
    fun decodeRawTransaction(
        @Body body: BtcRawTransaction,
        @CallbackSuccess onSuccess: (BtcDecodedRawTransaction) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("addresses/{addresses}/outputs")
    fun getOutputs(
        @Query("status") status: String,
        @Path("addresses") addresses: List<String>,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE,
        @CallbackSuccess onSuccess: (List<BtcOutput>) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("addresses/{addresses}")
    fun getAddressesWithBalances(
        @Path("addresses") addresses: List<String>,
        @CallbackSuccess onSuccess: (List<AddressWithBalance>) -> Unit,
        @CallbackError onError: (ApiException) -> Unit
    )

    @Get("estimate-fee")
    fun estimateFee(
        @CallbackSuccess onSuccess: (String) -> Unit,
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