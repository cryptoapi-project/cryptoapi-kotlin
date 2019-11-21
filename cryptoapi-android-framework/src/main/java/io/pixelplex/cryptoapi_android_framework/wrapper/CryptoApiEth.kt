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

/**
 * Describes ETH library service functionality
 *
 * Represents ETH library service,
 * that combines all methods which call Crypto API
 *
 * @author Sergey Krupenich
 */
interface CryptoApiEth {
    /**
     * Estimates ETH gas
     * @param ethEstimatedGasCallBody Request body which is a parameters object which allows to get Eth Estimate gas
     * @param onSuccess Callback of success response
     * @param onError Callback of an error response
     */
    fun estimateGas(
        ethEstimatedGasCallBody: EthEstimatedGasCallBody,
        onSuccess: (EthEstimatedGasResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    /**
     * Calls contract
     * @param ethContractCallBody Request body which is a parameters object which allows to call
     * an ETH contract
     * @param contractAddress Param of an ETH contract address
     * @param onSuccess Callback of success response
     * @param onError Callback of an error response
     */
    fun callContract(
        ethContractCallBody: EthContractCallBody,
        contractAddress: String,
        onSuccess: (EthCallContractResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    /**
     * Makes a raw transaction
     * @param ethTransactionRawCallBody Request body which is a parameters object which allows to
     * call a raw transaction
     * @param onSuccess Callback of success response
     * @param onError Callback of an error response
     */
    fun transactionsRawSend(
        ethTransactionRawCallBody: EthTransactionRawCallBody,
        onSuccess: (EthTransactionRawResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    /**
     * Decodes raw transaction
     * @param ethTransactionRawCallBody Request body which is a parameters object which allows to
     * decode raw transaction
     * @param onSuccess Callback of success response
     * @param onError Callback of an error response
     */
    fun transactionsRawDecode(
        ethTransactionRawCallBody: EthTransactionRawCallBody,
        onSuccess: (EthTransactionRawDecodeResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    /**
     * Gets network
     * @param onSuccess Callback of success response
     * @param onError Callback of an error response
     */
    fun getNetwork(
        onSuccess: (EthNetworkResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    /**
     * Gets balances
     * @param typedParams Request body which is a parameters object which allows to get ETH balances
     * @param onSuccess Callback of success response
     * @param onError Callback of an error response
     */
    fun getBalances(
        typedParams: EthTypedParams,
        onSuccess: (EthBalanceResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    /**
     * Gets ETH info
     * @param typedParams Request body which is a parameters object which allows to get ETH info
     * @param onSuccess Callback of success response
     * @param onError Callback of an error response
     */
    fun getEthInfo(
        typedParams: EthTypedParams,
        onSuccess: (EthInfoResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    /**
     * Gets ETH transfers
     * @param ethTransferCallBody Request body which is a parameters object which allows to get ETH
     * transfers
     * @param onSuccess Callback of success response
     * @param onError Callback of an error response
     */
    fun getEthTransfers(
        ethTransferCallBody: EthTransferCallBody,
        onSuccess: (EthTransferResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    /**
     * Gets ETH external transaction
     * @param ethEthTransactionExternalCallBody Request body which is a parameters object which
     * allows to get Eth external transaction
     * @param onSuccess Callback of success response
     * @param onError Callback of an error response
     */
    fun getTransactionsExternal(
        ethEthTransactionExternalCallBody: EthTransactionExternalCallBody,
        onSuccess: (EthTransactionExternalResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    /**
     * Gets ETH transactions
     * @param ethTransactionCallBody Request body which is a parameters object which allows to get
     * Eth transaction
     * @param onSuccess Callback of success response
     * @param onError Callback of an error response
     */
    fun getEthTransactions(
        ethTransactionCallBody: EthTransactionCallBody,
        onSuccess: (EthTransactionsResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    /**
     * Gets ETH transaction by hash
     * @param hash Request param which is a parameters object which allows to get ETH transactions by
     * the hash
     * @param onSuccess Callback of success response
     * @param onError Callback of an error response
     */
    fun getEthTransactionsByHash(
        hash: String,
        onSuccess: (EthTransactionResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    /**
     * Gets ETH contract info
     * @param address Request param which is a parameters object which allows to get Eth contracts info
     * @param onSuccess Callback of success response
     * @param onError Callback of an error response
     */
    fun getEthContractsInfo(
        address: String,
        onSuccess: (EthContractBytecodeResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    /**
     * Gets token balances
     * @param ethTokenBalanceCallBody Request body which is a parameters object which allows to ETH
     * tokens balances
     * @param onSuccess Callback of success response
     * @param onError Callback of an error response
     */
    fun getTokensBalances(
        ethTokenBalanceCallBody: EthTokenBalanceCallBody,
        onSuccess: (EthTokenBalanceResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    /**
     * Gets token transfers
     * @param ethTokenTransferCallBody Request body which is a parameters object which allows to get
     * ETH tokens transfers
     * @param onSuccess Callback of success response
     * @param onError Callback of an error response
     */
    fun getTokensTransfers(
        ethTokenTransferCallBody: EthTokenTransferCallBody,
        onSuccess: (EthTokenTransferResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    /**
     * Gets ETH token info
     * @param tokenAddress Request body which is a parameters object which allows to get Eth token info
     * @param onSuccess Callback of success response
     * @param onError Callback of an error response
     */
    fun getTokenInfo(
        tokenAddress: String,
        onSuccess: (EthTokenInfoResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )

    /**
     * Searches some specific tokens
     * @param ethTokenSearchCallBody Request body which is a parameters object which allows to
     * search tokens
     * @param onSuccess Callback of success response
     * @param onError Callback of an error response
     */
    fun getTokensSearch(
        ethTokenSearchCallBody: EthTokenSearchCallBody,
        onSuccess: (EthTokenSearchResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    )
}