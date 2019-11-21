package io.pixelplex.cryptoapi_android_framework.wrapper

import io.pixelplex.model.data.EthTypedParams
import io.pixelplex.model.data.EthTransfer
import io.pixelplex.model.exception.NetworkException
import io.pixelplex.model.data.EthContractBytecodeResponse
import io.pixelplex.model.data.EthContractCallBody
import io.pixelplex.model.data.EthTransaction
import io.pixelplex.model.data.EthTransactionRawBody
import io.pixelplex.model.data.TransactionExternal
import io.pixelplex.model.data.EstimatedGasBody
import io.pixelplex.model.data.EthTokensBalancesBody
import io.pixelplex.model.data.EthTokensSearchBody
import io.pixelplex.model.data.TokensTransfersCallBody
import io.pixelplex.model.response.*

interface CryptoApiEth {
    fun estimateGas(
        estimatedGasBody: EstimatedGasBody,
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
        ethTransactionRawBody: EthTransactionRawBody,
        onSuccess: (EthTransactionRawResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    fun transactionsRawDecode(
        ethTransactionRawBody: EthTransactionRawBody,
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
        ethTransfer: EthTransfer,
        onSuccess: (EthTransferResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    fun getTransactionsExternal(
        ethTransactionExternal: TransactionExternal,
        onSuccess: (TransactionExternalResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    fun getEthTransactions(
        ethTransaction: EthTransaction,
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
        ethTokensBalancesBody: EthTokensBalancesBody,
        onSuccess: (EthTokensBalancesResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    fun getTokensTransfers(
        tokensTransfersCallBody: TokensTransfersCallBody,
        onSuccess: (EthTokensTransfersResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    fun getTokenInfo(
        tokenAddress: String,
        onSuccess: (EthTokenInfoResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    fun getTokensSearch(
        ethTokensSearchBody: EthTokensSearchBody,
        onSuccess: (EthTokenSearchResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )
}