package io.pixelplex.cryptoapi_android_framework.wrapper

import com.google.gson.Gson
import io.pixelplex.cryptoapi_android_framework.core.CryptoApi
import io.pixelplex.cryptoapi_android_framework.core.CryptoApi.RequestMethod.POST
import io.pixelplex.cryptoapi_android_framework.support.fromJson
import io.pixelplex.model.data.EthContractCallBody
import io.pixelplex.model.data.EthEstimatedGasCallBody
import io.pixelplex.model.data.EthTokenBalanceCallBody
import io.pixelplex.model.data.EthTokenSearchCallBody
import io.pixelplex.model.data.EthTokenTransferCallBody
import io.pixelplex.model.data.EthTransactionCallBody
import io.pixelplex.model.data.EthTransactionExternalCallBody
import io.pixelplex.model.data.EthTransactionRawCallBody
import io.pixelplex.model.data.EthTransferCallBody
import io.pixelplex.model.data.EthTypedParams
import io.pixelplex.model.response.ErrorResponse
import io.pixelplex.model.response.EthBalanceResponse
import io.pixelplex.model.response.EthCallContractResponse
import io.pixelplex.model.response.EthContractBytecodeResponse
import io.pixelplex.model.response.EthEstimatedGasResponse
import io.pixelplex.model.response.EthInfoResponse
import io.pixelplex.model.response.EthNetworkResponse
import io.pixelplex.model.response.EthTokenBalanceResponse
import io.pixelplex.model.response.EthTokenInfoResponse
import io.pixelplex.model.response.EthTokenSearchResponse
import io.pixelplex.model.response.EthTokenTransferResponse
import io.pixelplex.model.response.EthTransactionExternalResponse
import io.pixelplex.model.response.EthTransactionRawDecodeResponse
import io.pixelplex.model.response.EthTransactionRawResponse
import io.pixelplex.model.response.EthTransactionResponse
import io.pixelplex.model.response.EthTransactionsResponse
import io.pixelplex.model.response.EthTransferResponse

class CryptoApiEthImpl(
    private val cryptoApiClient: CryptoApi
) : CryptoApiEth {
    override fun estimateGas(
        ethEstimatedGasCallBody: EthEstimatedGasCallBody,
        onSuccess: (EthEstimatedGasResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = ESTIMATE_GAS_PARAM,
            method = POST,
            onSuccess = { responseJson -> onSuccess(fromJson(responseJson)) },
            onError = { responseJson -> onError(fromJson(responseJson)) },
            body = Gson().toJson(ethEstimatedGasCallBody, EthEstimatedGasCallBody::class.java)
        )
    }

    override fun callContract(
        ethContractCallBody: EthContractCallBody,
        contractAddress: String,
        onSuccess: (EthCallContractResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = CONTRACTS_CALL_PARAM.format(contractAddress),
            method = POST,
            onSuccess = { responseJson ->
                onSuccess(
                    EthCallContractResponse(
                        fromJson(responseJson)
                    )
                )
            },
            onError = { responseJson -> onError(fromJson(responseJson)) },
            body = Gson().toJson(ethContractCallBody, ethContractCallBody::class.java)
        )
    }

    override fun transactionsRawSend(
        ethTransactionRawCallBody: EthTransactionRawCallBody,
        onSuccess: (EthTransactionRawResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = ETH_TRANSACTIONS_RAW_SEND_PARAM,
            method = POST,
            onSuccess = { responseJson -> onSuccess(fromJson(responseJson)) },
            onError = { responseJson -> onError(fromJson(responseJson)) },
            body = Gson().toJson(ethTransactionRawCallBody, EthTransactionRawCallBody::class.java)
        )
    }

    override fun transactionsRawDecode(
        ethTransactionRawCallBody: EthTransactionRawCallBody,
        onSuccess: (EthTransactionRawDecodeResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = ETH_TRANSACTIONS_RAW_DECODE_PARAM,
            method = POST,
            onSuccess = { responseJson -> onSuccess(fromJson(responseJson)) },
            onError = { responseJson -> onError(fromJson(responseJson)) },
            body = Gson().toJson(ethTransactionRawCallBody, EthTransactionRawCallBody::class.java)
        )
    }

    override fun getNetwork(
        onSuccess: (EthNetworkResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = NETWORK_PARAM,
            onSuccess = { responseJson -> onSuccess(fromJson(responseJson)) },
            onError = { responseJson -> onError(fromJson(responseJson)) }
        )
    }

    override fun getBalances(
        typedParams: EthTypedParams,
        onSuccess: (EthBalanceResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = ACCOUNTS_ADDRESS_BALANCE_PARAM.format(typedParams.string()),
            onSuccess = { responseJson ->
                onSuccess(
                    EthBalanceResponse(
                        fromJson(responseJson)
                    )
                )
            },
            onError = { responseJson -> onError(fromJson(responseJson)) }
        )
    }

    override fun getEthInfo(
        typedParams: EthTypedParams,
        onSuccess: (EthInfoResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = ACCOUNTS_ADDRESS_INFO_PARAM.format(typedParams.string()),
            onSuccess = { responseJson ->
                onSuccess(
                    EthInfoResponse(
                        fromJson(responseJson)
                    )
                )
            },
            onError = { responseJson -> onError(fromJson(responseJson)) }
        )
    }

    override fun getEthTransfers(
        ethTransferCallBody: EthTransferCallBody,
        onSuccess: (EthTransferResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = ETH_TRANSFERS_PARAM.format(
                ethTransferCallBody.typedParams.string(),
                ethTransferCallBody.skip,
                ethTransferCallBody.limit,
                ethTransferCallBody.positive
            ),
            onSuccess = { responseJson -> onSuccess(fromJson(responseJson)) },
            onError = { responseJson -> onError(fromJson(responseJson)) }
        )
    }

    override fun getTransactionsExternal(
        ethEthTransactionExternalCallBody: EthTransactionExternalCallBody,
        onSuccess: (EthTransactionExternalResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = TRANSACTIONS_EXTERNAL_PARAM.format(
                ethEthTransactionExternalCallBody.typedParams.string(),
                ethEthTransactionExternalCallBody.skip,
                ethEthTransactionExternalCallBody.limit
            ),
            onSuccess = { responseJson -> onSuccess(fromJson(responseJson)) },
            onError = { responseJson -> onError(fromJson(responseJson)) }
        )
    }

    override fun getEthTransactions(
        ethTransactionCallBody: EthTransactionCallBody,
        onSuccess: (EthTransactionsResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = TRANSACTIONS_PARAM.format(
                ethTransactionCallBody.from,
                ethTransactionCallBody.to,
                ethTransactionCallBody.skip,
                ethTransactionCallBody.limit
            ),
            onSuccess = { responseJson -> onSuccess(fromJson(responseJson)) },
            onError = { responseJson -> onError(fromJson(responseJson)) }
        )
    }

    override fun getEthTransactionsByHash(
        hash: String,
        onSuccess: (EthTransactionResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = TRANSACTIONS_HASH_PARAM.format(hash),
            onSuccess = { responseJson -> onSuccess(fromJson(responseJson)) },
            onError = { responseJson -> onError(fromJson(responseJson)) }
        )
    }

    override fun getEthContractsInfo(
        address: String,
        onSuccess: (EthContractBytecodeResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = CONTRACTS_INFO_PARAM.format(address),
            onSuccess = { responseJson -> onSuccess(fromJson(responseJson)) },
            onError = { responseJson -> onError(fromJson(responseJson)) }
        )
    }

    override fun getTokensBalances(
        ethTokenBalanceCallBody: EthTokenBalanceCallBody,
        onSuccess: (EthTokenBalanceResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = ETH_TOKENS_BALANCES_PARAM.format(
                ethTokenBalanceCallBody.address,
                ethTokenBalanceCallBody.skip,
                ethTokenBalanceCallBody.limit
            ),
            onSuccess = { responseJson -> onSuccess(fromJson(responseJson)) },
            onError = { responseJson -> onError(fromJson(responseJson)) }
        )
    }

    override fun getTokensTransfers(
        ethTokenTransferCallBody: EthTokenTransferCallBody,
        onSuccess: (EthTokenTransferResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = ETH_TOKENS_TRANSFERS_PARAM.format(
                ethTokenTransferCallBody.token,
                ethTokenTransferCallBody.typedParams.string(),
                ethTokenTransferCallBody.skip,
                ethTokenTransferCallBody.limit
            ),
            onSuccess = { responseJson -> onSuccess(fromJson(responseJson)) },
            onError = { responseJson -> onError(fromJson(responseJson)) }
        )
    }

    override fun getTokenInfo(
        tokenAddress: String,
        onSuccess: (EthTokenInfoResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = ETH_TOKENS_INFO_PARAM.format(tokenAddress),
            onSuccess = { responseJson -> onSuccess(fromJson(responseJson)) },
            onError = { responseJson -> onError(fromJson(responseJson)) }
        )
    }

    override fun getTokensSearch(
        ethTokenSearchCallBody: EthTokenSearchCallBody,
        onSuccess: (EthTokenSearchResponse) -> Unit,
        onError: (ErrorResponse) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = ETH_TOKENS_SEARCH_PARAM.format(
                ethTokenSearchCallBody.query,
                ethTokenSearchCallBody.skip,
                ethTokenSearchCallBody.limit,
                ethTokenSearchCallBody.types.string()
            ),
            onSuccess = { responseJson -> onSuccess(fromJson(responseJson)) },
            onError = { responseJson -> onError(fromJson(responseJson)) }
        )
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
        private const val ETH_TOKENS_BALANCES_PARAM =
            "coins/eth/tokens/%s/balances?skip=%s&limit=%s"
        private const val ETH_TOKENS_TRANSFERS_PARAM =
            "coins/eth/tokens/%s/%s/transfers/?skip=%s&limit=%s"
        private const val ETH_TOKENS_INFO_PARAM = "coins/eth/tokens/%s/info"
        private const val ETH_TOKENS_SEARCH_PARAM =
            "coins/eth/tokens/search?query=%s&skip=%s&limit=%s&types=%s"
    }
}