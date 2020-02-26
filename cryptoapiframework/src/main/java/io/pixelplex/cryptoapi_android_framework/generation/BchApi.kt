package io.pixelplex.cryptoapi_android_framework.generation

import io.pixelplex.annotation.*
import io.pixelplex.model.data.btc.*
import io.pixelplex.model.exception.ApiException
import io.pixelplex.model.common.ApiResult

@Coin("bch")
interface BchApi {

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
}