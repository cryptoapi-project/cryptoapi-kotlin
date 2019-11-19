package io.pixelplex.cryptoapi_android_framework.wrapper

import io.pixelplex.cryptoapi_android_framework.core.model.data.EstimatedGas
import io.pixelplex.cryptoapi_android_framework.core.model.data.EthAddresses
import io.pixelplex.cryptoapi_android_framework.core.model.data.EthTransfer
import io.pixelplex.cryptoapi_android_framework.core.model.response.EstimatedGasResponse
import io.pixelplex.cryptoapi_android_framework.core.model.response.EthBalanceResponse
import io.pixelplex.cryptoapi_android_framework.core.model.response.EthInfoResponse
import io.pixelplex.cryptoapi_android_framework.core.model.response.EthNetworkResponse
import io.pixelplex.cryptoapi_android_framework.core.model.response.EthTransferResponse
import io.pixelplex.cryptoapi_android_framework.exception.NetworkException

interface CryptoApiEth {
    fun estimateGas(
        estimatedGas: EstimatedGas,
        onSuccess: (EstimatedGasResponse) -> Unit,
        onError: (NetworkException) -> Unit
    )

    fun getNetwork(
        onSuccess: (EthNetworkResponse) -> Unit,
        onError: (NetworkException) -> Unit
    )

    fun getBalances(
        addresses: EthAddresses,
        onSuccess: (EthBalanceResponse) -> Unit,
        onError: (NetworkException) -> Unit
    )

    fun getEthInfo(
        addresses: EthAddresses,
        onSuccess: (EthInfoResponse) -> Unit,
        onError: (NetworkException) -> Unit
    )

    fun getEthTransfers(
        ethTransfer: EthTransfer,
        onSuccess: (EthTransferResponse) -> Unit,
        onError: (NetworkException) -> Unit
    )
}