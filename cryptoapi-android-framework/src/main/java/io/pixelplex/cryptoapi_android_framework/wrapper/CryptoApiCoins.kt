package io.pixelplex.cryptoapi_android_framework.wrapper

import io.pixelplex.model.response.CoinsResponse
import io.pixelplex.model.response.ErrorResponse

/**
 * Describes coins library service functionality
 *
 * Represents coins library service,
 * that combines all methods which call Crypto API
 *
 * @author Sergey Krupenich
 */
interface CryptoApiCoins {

    /**
     * Returns all coins
     * @param onSuccess Callback of success response
     * @param onError Callback of an error response
     */
    fun getCoins(
        onSuccess: (CoinsResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )
}