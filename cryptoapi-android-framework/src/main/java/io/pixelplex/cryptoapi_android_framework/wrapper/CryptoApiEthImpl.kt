package io.pixelplex.cryptoapi_android_framework.wrapper

import com.google.gson.Gson
import io.pixelplex.cryptoapi_android_framework.core.CryptoApi
import io.pixelplex.cryptoapi_android_framework.core.CryptoApi.RequestMethod.POST
import io.pixelplex.cryptoapi_android_framework.core.model.data.EstimatedGas
import io.pixelplex.cryptoapi_android_framework.core.model.data.EthAddresses
import io.pixelplex.cryptoapi_android_framework.core.model.response.EstimatedGasResponse
import io.pixelplex.cryptoapi_android_framework.core.model.response.EthBalance
import io.pixelplex.cryptoapi_android_framework.core.model.response.EthBalanceResponse
import io.pixelplex.cryptoapi_android_framework.core.model.response.EthNetworkResponse
import io.pixelplex.cryptoapi_android_framework.exception.NetworkException
import io.pixelplex.cryptoapi_android_framework.support.fromJson
import io.pixelplex.cryptoapi_android_framework.support.isJSONArray

class CryptoApiEthImpl(
    private val cryptoApiClient: CryptoApi
): CryptoApiEth {
    override fun estimateGas(
        estimatedGas: EstimatedGas,
        onSuccess: (EstimatedGasResponse) -> Unit,
        onError: (NetworkException) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = ESTIMATE_GAS_PARAM,
            method = POST,
            onSuccess = { responseJson -> onSuccess(fromJson(responseJson)) },
            onError = onError,
            body = Gson().toJson(estimatedGas, EstimatedGas::class.java)
        )
    }

    override fun getNetwork(
        onSuccess: (EthNetworkResponse) -> Unit,
        onError: (NetworkException) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = NETWORK_PARAM,
            onSuccess = { responseJson -> onSuccess(fromJson(responseJson)) },
            onError = onError
        )
    }

    override fun getBalances(
        addresses: EthAddresses,
        onSuccess: (EthBalanceResponse) -> Unit,
        onError: (NetworkException) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = ACCOUNTS_ADDRESS_BALANCE_PARAM.format(addresses.string()),
            onSuccess = { responseJson ->
                successEthBalances(responseJson, onSuccess)
            },
            onError = onError
        )
    }

    private fun successEthBalances(
        responseJson: String,
        onSuccess: (EthBalanceResponse) -> Unit
    ) {
        if (responseJson.isJSONArray()) {
            onSuccess(
                EthBalanceResponse(
                    balances = fromJson<List<EthBalance>>(responseJson)
                )
            )
        } else {
            onSuccess(fromJson(responseJson))
        }
    }

    companion object {
        private const val ESTIMATE_GAS_PARAM = "coins/eth/estimate-gas"
        private const val NETWORK_PARAM = "coins/eth/network"
        private const val ACCOUNTS_ADDRESS_BALANCE_PARAM = "coins/eth/accounts/%s/balance"
    }
}