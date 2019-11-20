package io.pixelplex.cryptoapi_android_framework.wrapper

import io.pixelplex.model.data.EstimatedGas
import io.pixelplex.model.data.EthAddresses
import io.pixelplex.model.data.EthTransfer
import io.pixelplex.model.response.EstimatedGasResponse
import io.pixelplex.model.response.EthBalanceResponse
import io.pixelplex.model.response.EthInfoResponse
import io.pixelplex.model.response.EthNetworkResponse
import io.pixelplex.model.response.EthTransferResponse

import io.pixelplex.model.exception.NetworkException

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