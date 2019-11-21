package io.pixelplex.cryptoapi_android_framework.wrapper

import io.pixelplex.model.data.EthAddresses
import io.pixelplex.model.data.EthTransfer
import io.pixelplex.model.exception.NetworkException
import io.pixelplex.model.data.EthContractBytecodeResponse
import io.pixelplex.model.data.EthContractCallBody
import io.pixelplex.model.data.EthTransaction
import io.pixelplex.model.data.EthTransactionRawBody
import io.pixelplex.model.data.TransactionExternal
import io.pixelplex.model.data.EstimatedGasBody
import io.pixelplex.model.data.EthTokensBalancesBody
import io.pixelplex.model.data.TokensTransfersCallBody
import io.pixelplex.model.response.*

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

    fun getTokensBalances(
        ethTokensBalancesBody: EthTokensBalancesBody,
        onSuccess: (EthTokensBalancesResponse) -> Unit,
        onError: (NetworkException) -> Unit
    )

    fun getTokensTransfers(
        tokensTransfersCallBody: TokensTransfersCallBody,
        onSuccess: (EthTokensTransfersResponse) -> Unit,
        onError: (NetworkException) -> Unit
    )

    fun getTokenInfo(
        tokenAddress: String,
        onSuccess: (EthTokenInfoResponse) -> Unit,
        onError: (NetworkException) -> Unit
    )
}