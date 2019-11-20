package io.pixelplex.cryptoapi_android_framework.wrapper

import com.google.gson.Gson
import io.pixelplex.cryptoapi_android_framework.core.CryptoApi
import io.pixelplex.cryptoapi_android_framework.core.CryptoApi.RequestMethod.POST

import io.pixelplex.model.data.EthAddresses
import io.pixelplex.model.data.EthTransfer
import io.pixelplex.model.response.EstimatedGasResponse
import io.pixelplex.model.response.EthBalance
import io.pixelplex.model.response.EthBalanceResponse
import io.pixelplex.model.response.EthInfo
import io.pixelplex.model.response.EthInfoResponse
import io.pixelplex.model.response.EthNetworkResponse
import io.pixelplex.model.response.EthTransferResponse

import io.pixelplex.cryptoapi_android_framework.support.fromJson
import io.pixelplex.cryptoapi_android_framework.support.isJSONArray
import io.pixelplex.model.exception.NetworkException
import io.pixelplex.model.data.EthContractBytecodeResponse
import io.pixelplex.model.data.EthContractCallBody
import io.pixelplex.model.data.EthTransaction
import io.pixelplex.model.data.EthTransactionRawBody
import io.pixelplex.model.data.TransactionExternal
import io.pixelplex.model.response.EthCallContractResponse
import io.pixelplex.model.response.EthTransactionRawResponse
import io.pixelplex.model.response.EthTransactionResponse
import io.pixelplex.model.response.EthTransactionsResponse
import io.pixelplex.model.response.TransactionExternalResponse

import io.pixelplex.cryptoapi_android_framework.support.isNotJSON
import io.pixelplex.model.data.EstimatedGasBody

class CryptoApiEthImpl(
    private val cryptoApiClient: CryptoApi
): CryptoApiEth {
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

    override fun getEthInfo(
        addresses: EthAddresses,
        onSuccess: (EthInfoResponse) -> Unit,
        onError: (NetworkException) -> Unit
    ) {
        cryptoApiClient.callApi(
            params = ACCOUNTS_ADDRESS_INFO_PARAM.format(addresses.string()),
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
                ethTransfer.addresses.string(),
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
                ethTransactionExternal.addresses.string(),
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
        private const val ETH_TRANSFERS_PARAM = "coins/eth/accounts/%s/transfers?skip=%s&limit=%s&positive=%s"
        private const val TRANSACTIONS_EXTERNAL_PARAM = "coins/eth/accounts/%s/transactions/external?skip=%s&limit=%s"
        private const val TRANSACTIONS_PARAM = "coins/eth/transactions?from=%s&to=%s&skip=%s&limit=%s"
        private const val TRANSACTIONS_HASH_PARAM = "coins/eth/transactions/%s"
        private const val CONTRACTS_INFO_PARAM = "coins/eth/contracts/%s/info"
        private const val CONTRACTS_CALL_PARAM = "coins/eth/contracts/%s/call"
        private const val ETH_TRANSACTIONS_RAW_SEND_PARAM = "coins/eth/transactions/raw/send"
    }
}