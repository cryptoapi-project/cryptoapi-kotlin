package io.pixelplex.cryptoapi_android_framework.wrapper

import com.google.gson.Gson
import io.pixelplex.cryptoapi_android_framework.core.CryptoApi
import io.pixelplex.cryptoapi_android_framework.core.CryptoApi.RequestMethod.POST

import io.pixelplex.model.data.EthTypedParams
import io.pixelplex.model.data.EthTransfer

import io.pixelplex.cryptoapi_android_framework.support.fromJson
import io.pixelplex.cryptoapi_android_framework.support.isJSONArray
import io.pixelplex.model.exception.NetworkException
import io.pixelplex.model.data.EthContractBytecodeResponse
import io.pixelplex.model.data.EthContractCallBody
import io.pixelplex.model.data.EthTransaction
import io.pixelplex.model.data.EthTransactionRawBody
import io.pixelplex.model.data.TransactionExternal

import io.pixelplex.cryptoapi_android_framework.support.isNotJSON
import io.pixelplex.model.data.EstimatedGasBody
import io.pixelplex.model.data.EthTokensBalancesBody
import io.pixelplex.model.data.EthTokensSearchBody
import io.pixelplex.model.data.TokensTransfersCallBody
import io.pixelplex.model.response.*

class CryptoApiEthImpl(
    private val cryptoApiClient: CryptoApi
) : CryptoApiEth {
    override fun estimateGas(
        estimatedGasBody: EstimatedGasBody,
        onSuccess: (EstimatedGasResponse) -> Unit,
        onError: (NetworkException) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = ESTIMATE_GAS_PARAM,
            method = POST,
            onSuccess = { responseJson -> onSuccess(fromJson(responseJson)) },
            onError = onError,
            body = Gson().toJson(estimatedGasBody, EstimatedGasBody::class.java)
        )
    }

    override fun callContract(
        ethContractCallBody: EthContractCallBody,
        contractAddress: String,
        onSuccess: (EthCallContractResponse) -> Unit,
        onError: (NetworkException) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = CONTRACTS_CALL_PARAM.format(contractAddress),
            method = POST,
            onSuccess = { responseJson ->
                successEthContractCall(responseJson, onSuccess)
            },
            onError = onError,
            body = Gson().toJson(ethContractCallBody, ethContractCallBody::class.java)
        )
    }

    override fun transactionsRawSend(
        ethTransactionRawBody: EthTransactionRawBody,
        onSuccess: (EthTransactionRawResponse) -> Unit,
        onError: (NetworkException) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = ETH_TRANSACTIONS_RAW_SEND_PARAM,
            method = POST,
            onSuccess = { responseJson ->
                successEthTransactionsRawSend(responseJson, onSuccess)
            },
            onError = onError,
            body = Gson().toJson(ethTransactionRawBody, EthTransactionRawBody::class.java)
        )
    }

    override fun transactionsRawDecode(
        ethTransactionRawBody: EthTransactionRawBody,
        onSuccess: (EthTransactionRawDecodeResponse) -> Unit,
        onError: (NetworkException) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = ETH_TRANSACTIONS_RAW_DECODE_PARAM,
            method = POST,
            onSuccess = { responseJson ->
                onSuccess(fromJson(responseJson))
            },
            onError = onError,
            body = Gson().toJson(ethTransactionRawBody, EthTransactionRawBody::class.java)
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
        typedParams: EthTypedParams,
        onSuccess: (EthBalanceResponse) -> Unit,
        onError: (NetworkException) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = ACCOUNTS_ADDRESS_BALANCE_PARAM.format(typedParams.string()),
            onSuccess = { responseJson ->
                successEthBalances(responseJson, onSuccess)
            },
            onError = onError
        )
    }

    override fun getEthInfo(
        typedParams: EthTypedParams,
        onSuccess: (EthInfoResponse) -> Unit,
        onError: (NetworkException) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = ACCOUNTS_ADDRESS_INFO_PARAM.format(typedParams.string()),
            onSuccess = { responseJson ->
                successEthInfo(responseJson, onSuccess)
            },
            onError = onError
        )
    }

    override fun getEthTransfers(
        ethTransfer: EthTransfer,
        onSuccess: (EthTransferResponse) -> Unit,
        onError: (NetworkException) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = ETH_TRANSFERS_PARAM.format(
                ethTransfer.typedParams.string(),
                ethTransfer.skip,
                ethTransfer.limit,
                ethTransfer.positive
            ),
            onSuccess = { responseJson -> onSuccess(fromJson(responseJson)) },
            onError = onError
        )
    }

    override fun getTransactionsExternal(
        ethTransactionExternal: TransactionExternal,
        onSuccess: (TransactionExternalResponse) -> Unit,
        onError: (NetworkException) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = TRANSACTIONS_EXTERNAL_PARAM.format(
                ethTransactionExternal.typedParams.string(),
                ethTransactionExternal.skip,
                ethTransactionExternal.limit
            ),
            onSuccess = { responseJson -> onSuccess(fromJson(responseJson)) },
            onError = onError
        )
    }

    override fun getEthTransactions(
        ethTransaction: EthTransaction,
        onSuccess: (EthTransactionsResponse) -> Unit,
        onError: (NetworkException) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = TRANSACTIONS_PARAM.format(
                ethTransaction.from,
                ethTransaction.to,
                ethTransaction.skip,
                ethTransaction.limit
            ),
            onSuccess = { responseJson -> onSuccess(fromJson(responseJson)) },
            onError = onError
        )
    }

    override fun getEthTransactionsByHash(
        hash: String,
        onSuccess: (EthTransactionResponse) -> Unit,
        onError: (NetworkException) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = TRANSACTIONS_HASH_PARAM.format(hash),
            onSuccess = { responseJson -> onSuccess(fromJson(responseJson)) },
            onError = onError
        )
    }

    override fun getEthContractsInfo(
        address: String,
        onSuccess: (EthContractBytecodeResponse) -> Unit,
        onError: (NetworkException) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = CONTRACTS_INFO_PARAM.format(address),
            onSuccess = { responseJson -> onSuccess(fromJson(responseJson)) },
            onError = onError
        )
    }

    override fun getTokensBalances(
        ethTokensBalancesBody: EthTokensBalancesBody,
        onSuccess: (EthTokensBalancesResponse) -> Unit,
        onError: (NetworkException) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = ETH_TOKENS_BALANCES_PARAM.format(
                ethTokensBalancesBody.address,
                ethTokensBalancesBody.skip,
                ethTokensBalancesBody.limit
            ),
            onSuccess = { responseJson -> onSuccess(fromJson(responseJson)) },
            onError = onError
        )
    }

    override fun getTokensTransfers(
        tokensTransfersCallBody: TokensTransfersCallBody,
        onSuccess: (EthTokensTransfersResponse) -> Unit,
        onError: (NetworkException) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = ETH_TOKENS_TRANSFERS_PARAM.format(
                tokensTransfersCallBody.token,
                tokensTransfersCallBody.typedParams.string(),
                tokensTransfersCallBody.skip,
                tokensTransfersCallBody.limit
            ),
            onSuccess = { responseJson -> onSuccess(fromJson(responseJson)) },
            onError = onError
        )
    }

    override fun getTokenInfo(
        tokenAddress: String,
        onSuccess: (EthTokenInfoResponse) -> Unit,
        onError: (NetworkException) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = ETH_TOKENS_INFO_PARAM.format(tokenAddress),
            onSuccess = { responseJson -> onSuccess(fromJson(responseJson)) },
            onError = onError
        )
    }

    override fun getTokensSearch(
        ethTokensSearchBody: EthTokensSearchBody,
        onSuccess: (EthTokenSearchResponse) -> Unit,
        onError: (NetworkException) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = ETH_TOKENS_SEARCH_PARAM.format(
                ethTokensSearchBody.query,
                ethTokensSearchBody.skip,
                ethTokensSearchBody.limit,
                ethTokensSearchBody.types.string()
            ),
            onSuccess = { responseJson -> onSuccess(fromJson(responseJson)) },
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

    private fun successEthInfo(
        responseJson: String,
        onSuccess: (EthInfoResponse) -> Unit
    ) {
        if (responseJson.isJSONArray()) {
            onSuccess(
                EthInfoResponse(
                    info = fromJson<List<EthInfo>>(responseJson)
                )
            )
        } else {
            onSuccess(fromJson(responseJson))
        }
    }

    private fun successEthContractCall(
        responseJson: String,
        onSuccess: (EthCallContractResponse) -> Unit
    ) {
        if (responseJson.isNotJSON()) {
            onSuccess(
                EthCallContractResponse(
                    response = responseJson
                )
            )
        } else {
            onSuccess(fromJson(responseJson))
        }
    }

    private fun successEthTransactionsRawSend(
        responseJson: String,
        onSuccess: (EthTransactionRawResponse) -> Unit
    ) {
        if (responseJson.isNotJSON()) {
            onSuccess(
                EthTransactionRawResponse(
                    hash = responseJson
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
        private const val ACCOUNTS_ADDRESS_INFO_PARAM = "coins/eth/accounts/%s/info"
        private const val ETH_TRANSFERS_PARAM =
            "coins/eth/accounts/%s/transfers?skip=%s&limit=%s&positive=%s"
        private const val TRANSACTIONS_EXTERNAL_PARAM =
            "coins/eth/accounts/%s/transactions/external?skip=%s&limit=%s"
        private const val TRANSACTIONS_PARAM =
            "coins/eth/transactions?from=%s&to=%s&skip=%s&limit=%s"
        private const val TRANSACTIONS_HASH_PARAM = "coins/eth/transactions/%s"
        private const val CONTRACTS_INFO_PARAM = "coins/eth/contracts/%s/info"
        private const val CONTRACTS_CALL_PARAM = "coins/eth/contracts/%s/call"
        private const val ETH_TRANSACTIONS_RAW_SEND_PARAM = "coins/eth/transactions/raw/send"
        private const val ETH_TRANSACTIONS_RAW_DECODE_PARAM = "coins/eth/transactions/raw/decode"
        private const val ETH_TOKENS_BALANCES_PARAM = "coins/eth/tokens/%s/balances?skip=%s&limit=%s"
        private const val ETH_TOKENS_TRANSFERS_PARAM = "coins/eth/tokens/%s/%s/transfers/?skip=%s&limit=%s"
        private const val ETH_TOKENS_INFO_PARAM = "coins/eth/tokens/%s/info"
        private const val ETH_TOKENS_SEARCH_PARAM = "coins/eth/tokens/search?query=%s&skip=%s&limit=%s&types=%s"
    }
}