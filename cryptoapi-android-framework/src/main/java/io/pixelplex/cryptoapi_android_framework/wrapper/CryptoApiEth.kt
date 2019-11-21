package io.pixelplex.cryptoapi_android_framework.wrapper

import io.pixelplex.model.data.EthTypedParams
import io.pixelplex.model.data.EthTransferCallBody
import io.pixelplex.model.response.EthContractBytecodeResponse
import io.pixelplex.model.data.EthContractCallBody
import io.pixelplex.model.data.EthTransactionCallBody
import io.pixelplex.model.data.EthTransactionRawCallBody
import io.pixelplex.model.data.EthTransactionExternalCallBody
import io.pixelplex.model.data.EthEstimatedGasCallBody
import io.pixelplex.model.data.EthTokenBalanceCallBody
import io.pixelplex.model.data.EthTokenSearchCallBody
import io.pixelplex.model.data.EthTokenTransferCallBody
import io.pixelplex.model.response.*

interface CryptoApiEth {
    fun estimateGas(
        ethEstimatedGasCallBody: EthEstimatedGasCallBody,
        onSuccess: (EstimatedGasResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    fun callContract(
        ethContractCallBody: EthContractCallBody,
        contractAddress: String,
        onSuccess: (EthCallContractResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    fun transactionsRawSend(
        ethTransactionRawCallBody: EthTransactionRawCallBody,
        onSuccess: (EthTransactionRawResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    fun transactionsRawDecode(
        ethTransactionRawCallBody: EthTransactionRawCallBody,
        onSuccess: (EthTransactionRawDecodeResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    fun getNetwork(
        onSuccess: (EthNetworkResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    fun getBalances(
        typedParams: EthTypedParams,
        onSuccess: (EthBalanceResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    fun getEthInfo(
        typedParams: EthTypedParams,
        onSuccess: (EthInfoResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    fun getEthTransfers(
        ethTransferCallBody: EthTransferCallBody,
        onSuccess: (EthTransferResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    fun getTransactionsExternal(
        ethEthTransactionExternalCallBody: EthTransactionExternalCallBody,
        onSuccess: (TransactionExternalResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    fun getEthTransactions(
        ethTransactionCallBody: EthTransactionCallBody,
        onSuccess: (EthTransactionsResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    fun getEthTransactionsByHash(
        hash: String,
        onSuccess: (EthTransactionResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    fun getEthContractsInfo(
        address: String,
        onSuccess: (EthContractBytecodeResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    fun getTokensBalances(
        ethTokenBalanceCallBody: EthTokenBalanceCallBody,
        onSuccess: (EthTokensBalancesResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    fun getTokensTransfers(
        ethTokenTransferCallBody: EthTokenTransferCallBody,
        onSuccess: (EthTokensTransfersResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    fun getTokenInfo(
        tokenAddress: String,
        onSuccess: (EthTokenInfoResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    fun getTokensSearch(
        ethTokenSearchCallBody: EthTokenSearchCallBody,
        onSuccess: (EthTokenSearchResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )
}