package io.pixelplex.cryptoapi_android_framework.wrapper

import com.google.gson.Gson
import io.pixelplex.cryptoapi_android_framework.core.CryptoApi
import io.pixelplex.cryptoapi_android_framework.core.CryptoApi.RequestMethod.POST
import io.pixelplex.cryptoapi_android_framework.core.model.data.EstimatedGas
import io.pixelplex.cryptoapi_android_framework.core.model.response.EstimatedGasResponse
import io.pixelplex.cryptoapi_android_framework.exception.NetworkException
import io.pixelplex.cryptoapi_android_framework.support.fromJson

class CryptoApiEthImpl(
    private val cryptoApiClient: CryptoApi
): CryptoApiEth {
    override fun estimateGas(
        estimatedGas: EstimatedGas,
        onSuccess: (EstimatedGasResponse) -> Unit,
        onError: (NetworkException) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = COINS_PARAM,
            method = POST,
            onSuccess = { responseJson -> onSuccess(fromJson(responseJson)) },
            onError = onError,
            body = Gson().toJson(estimatedGas, EstimatedGas::class.java)
        )
    }

    companion object {
        private const val COINS_PARAM = "coins/eth/estimate-gas"
    }
}