package io.pixelplex.web3jexample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.pixelplex.cryptoapi_android_framework.CryptoApiFramework
import io.pixelplex.cryptoapi_android_framework.core.CryptoApi
import io.pixelplex.model.data.eth.EthEstimatedGasCall
import io.pixelplex.model.data.eth.EthTransactionRawCall
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.web3j.crypto.Credentials
import org.web3j.crypto.RawTransaction
import org.web3j.crypto.TransactionEncoder
import org.web3j.utils.Numeric
import java.math.BigInteger

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

    private val sendAmountBigInt = BigInteger.valueOf(SEND_AMOUNT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            val estimationGas = apiClient.estimateGas(estimatedGas)

            val rawTransaction = RawTransaction.createEtherTransaction(
                BigInteger.valueOf(estimationGas.nonce.toLong()),
                BigInteger.valueOf(estimationGas.gasPrice),
                BigInteger.valueOf(estimationGas.estimateGas),
                ETH_ADDRESS_2,
                sendAmountBigInt
            )

            val signedMessage =
                TransactionEncoder.signMessage(rawTransaction, Credentials.create(PRIVATE_KEY))
            val hexValue = Numeric.toHexString(signedMessage)

            val result = apiClient.sendRawTransaction(EthTransactionRawCall(hexValue))
            logD(result)
        }
    }

    private fun logD(value: String) {
        Log.d(WEB3J_ETH_EXAMPLE_KEY, value)
    }

    companion object {
        private const val CRYPTO_API_KEY = "YOUR_CRYPTO_API_KEY"
        private const val PRIVATE_KEY = "YOUR_PRIVATE_KEY"

        private const val ETH_ADDRESS_1 = "SENDER_ADDRESS"
        private const val ETH_ADDRESS_2 = "RECIPIENT_ADDRESS"

        private const val CALL_TIMEOUT = 30000L
        private const val READ_TIMEOUT = 30000L
        private const val CONNECT_TIMEOUT = 15000L

        private const val SEND_AMOUNT = 10000000000

        private const val WEB3J_ETH_EXAMPLE_KEY = "ETH_LOG"
    }
}