package io.pixelplex.cryptoapi_android_framework.wrapper

import io.pixelplex.cryptoapi_android_framework.core.model.data.EstimatedGasBody
import io.pixelplex.cryptoapi_android_framework.core.model.data.EthAddresses
import io.pixelplex.cryptoapi_android_framework.core.model.data.EthContractBytecodeResponse
import io.pixelplex.cryptoapi_android_framework.core.model.data.EthContractCallBody
import io.pixelplex.cryptoapi_android_framework.core.model.data.EthTransaction
import io.pixelplex.cryptoapi_android_framework.core.model.data.EthTransactionRawBody
import io.pixelplex.cryptoapi_android_framework.core.model.data.EthTransfer
import io.pixelplex.cryptoapi_android_framework.core.model.data.TransactionExternal
import io.pixelplex.cryptoapi_android_framework.core.model.response.EstimatedGasResponse
import io.pixelplex.cryptoapi_android_framework.core.model.response.EthBalanceResponse
import io.pixelplex.cryptoapi_android_framework.core.model.response.EthCallContractResponse
import io.pixelplex.cryptoapi_android_framework.core.model.response.EthInfoResponse
import io.pixelplex.cryptoapi_android_framework.core.model.response.EthNetworkResponse
import io.pixelplex.cryptoapi_android_framework.core.model.response.EthTransactionRawDecodeResponse
import io.pixelplex.cryptoapi_android_framework.core.model.response.EthTransactionRawResponse
import io.pixelplex.cryptoapi_android_framework.core.model.response.EthTransactionResponse
import io.pixelplex.cryptoapi_android_framework.core.model.response.EthTransactionsResponse
import io.pixelplex.cryptoapi_android_framework.core.model.response.EthTransferResponse
import io.pixelplex.cryptoapi_android_framework.core.model.response.TransactionExternalResponse
import io.pixelplex.cryptoapi_android_framework.exception.NetworkException

interface CryptoApiEth {
    fun estimateGas(
        estimatedGasBody: EstimatedGasBody,
        onSuccess: (EstimatedGasResponse) -> Unit,
        onError: (NetworkException) -> Unit
    )

    fun callContract(
        ethContractCallBody: EthContractCallBody,
        contractAddress: String,
        onSuccess: (EthCallContractResponse) -> Unit,
        onError: (NetworkException) -> Unit
    )

    fun transactionsRawSend(
        ethTransactionRawBody: EthTransactionRawBody,
        onSuccess: (EthTransactionRawResponse) -> Unit,
        onError: (NetworkException) -> Unit
    )

    fun transactionsRawDecode(
        ethTransactionRawBody: EthTransactionRawBody,
        onSuccess: (EthTransactionRawDecodeResponse) -> Unit,
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

    fun getTransactionsExternal(
        ethTransactionExternal: TransactionExternal,
        onSuccess: (TransactionExternalResponse) -> Unit,
        onError: (NetworkException) -> Unit
    )

    fun getEthTransactions(
        ethTransaction: EthTransaction,
        onSuccess: (EthTransactionsResponse) -> Unit,
        onError: (NetworkException) -> Unit
    )

    fun getEthTransactionsByHash(
        hash: String,
        onSuccess: (EthTransactionResponse) -> Unit,
        onError: (NetworkException) -> Unit
    )

    fun getEthContractsInfo(
        address: String,
        onSuccess: (EthContractBytecodeResponse) -> Unit,
        onError: (NetworkException) -> Unit
    )
}