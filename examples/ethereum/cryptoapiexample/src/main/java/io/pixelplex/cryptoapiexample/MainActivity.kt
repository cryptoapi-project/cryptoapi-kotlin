package io.pixelplex.cryptoapiexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.pixelplex.cryptoapi_android_framework.CryptoApiFramework
import io.pixelplex.cryptoapi_android_framework.core.CryptoApi
import io.pixelplex.model.data.eth.EthEstimatedGasCall
import io.pixelplex.model.data.eth.EthTransactionRawCall
import io.pixelplex.model.data.eth.EthTypedParams
import io.pixelplex.model.exception.ApiException
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val apiClient by lazy {
        CryptoApiFramework.getInstance(
            CALL_TIMEOUT,
            CONNECT_TIMEOUT,
            READ_TIMEOUT,
            CRYPTO_API_KEY,
            CryptoApi.URL.TESTNET
        ).ethereumAsyncApi
    }

    private val estimatedGas = EthEstimatedGasCall(
        from = ETH_ADDRESS_1,
        to = ETH_ADDRESS_2,
        value = "10"
    )

    private val ethTransactionRawBody =
        EthTransactionRawCall(
            tx = TX
        )

    private val ethAddresses = EthTypedParams(
        ETH_ADDRESS_1,
        ETH_ADDRESS_2
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            val estimationGas = apiClient.estimateGas(estimatedGas)
            logD(estimationGas.estimateGas.toString())

            val balances = apiClient.getBalances(ethAddresses.getList())
            balances.forEach {
                logD(it.balance)
            }

            try {
                val result = apiClient.sendRawTransaction(ethTransactionRawBody)
                logD(result)
            } catch (e: Exception) {
                val errors = (e as ApiException).errors
                errors.forEach {
                    logD(it.message)
                }
            }
        }
    }

    private fun logD(value: String) {
        Log.d(CRYPTO_API_ETH_EXAMPLE_KEY, value)
    }

    companion object {
        private const val CRYPTO_API_ETH_EXAMPLE_KEY = "ETH_LOG"

        private const val CALL_TIMEOUT = 30000L
        private const val READ_TIMEOUT = 30000L
        private const val CONNECT_TIMEOUT = 15000L

        private const val CRYPTO_API_KEY = "YOUR_TOKEN"

        private const val ETH_ADDRESS_1 = "0x141d5937C7b0e4fB4C535c900C0964B4852007eA"
        private const val ETH_ADDRESS_2 = "0xb0202eBbF797Dd61A3b28d5E82fbA2523edc1a9B"

        private const val TX =
            "0xf86e8386ca038602bba7f5220083632ea0941de29f644d555fe9cc3241e1083de0868f959bfa8545d964b800801ca04ef1f13c58af9a9ac4be66b838a238b24db798d585d882865637fdc35bdc49c4a04b7d1dfc3d9672080347a0d3559628f5f757bd6f6a005d1c4f7cdccce020ea02"
    }
}
