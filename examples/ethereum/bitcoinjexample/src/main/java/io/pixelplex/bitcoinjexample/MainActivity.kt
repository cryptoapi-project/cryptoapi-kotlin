package io.pixelplex.bitcoinjexample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.pixelplex.bitcoinjexample.ethereum_components.EthECKey
import io.pixelplex.bitcoinjexample.ethereum_components.EthTransaction
import io.pixelplex.cryptoapi_android_framework.CryptoApiFramework
import io.pixelplex.cryptoapi_android_framework.core.CryptoApi
import io.pixelplex.model.data.eth.EthEstimatedGasCall
import io.pixelplex.model.data.eth.EthTransactionRawCall
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.bouncycastle.util.encoders.Hex
import java.math.BigInteger

/**
 * Contains functionality associated with Crypto API Library using with Web3j
 *
 * @author Sergey Krupenich
 */
class MainActivity : AppCompatActivity() {

    /**
     * Crypto API libarary initializing
     */
    private val apiClient by lazy {
        CryptoApiFramework.getInstance(
            CALL_TIMEOUT,
            CONNECT_TIMEOUT,
            READ_TIMEOUT,
            CRYPTO_API_KEY,
            CryptoApi.URL.TESTNET
        ).ethereumAsyncApi
    }

    /**
     * Estimation gas object initializing
     */
    private val estimatedGas = EthEstimatedGasCall(
        from = ETH_ADDRESS_1,
        to = ETH_ADDRESS_2,
        value = "10"
    )

    /**
     * Send amount Big integer object initializing
     */
    private val sendAmountBigInt =
        BigInteger.valueOf(SEND_AMOUNT)

    /**
     * Your private key byte array
     */
    private val privateKey = ByteArray(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        GlobalScope.launch {
            // Estimation gas obtaining
            val estimationGas = apiClient.estimateGas(estimatedGas)

            // No need a prefix
            val addressWithoutPrefix = ETH_ADDRESS_2.removePrefix(PREFIX)

            // Address bytes getting from address (which has no any prefix)
            val addressBytes = Hex.decode(addressWithoutPrefix)

            // Transaction initialization
            val transaction =
                EthTransaction(
                    BigInteger.valueOf(estimationGas.nonce.toLong()).toBytes(),
                    BigInteger.valueOf(estimationGas.gasPrice).toBytes(),
                    BigInteger.valueOf(estimationGas.estimateGas).toBytes(),
                    addressBytes,
                    sendAmountBigInt.toBytes(),
                    null
                )

            // Sender key getting from your private key
            val senderKey = EthECKey.fromPrivate(privateKey)

            // Transaction signing
            transaction.sign(senderKey)

            // Transaction Tx getting
            val tx = getTx(Hex.toHexString(transaction.encoded))

            // Raw transaction Sending
            val result = apiClient.sendRawTransaction(EthTransactionRawCall(tx))
            logD(result)
        }
    }

    /**
     * A private method that makes a transaction Tx
     */
    private fun getTx(hex: String) =
        "$PREFIX${hex}"

    /**
     * Omitting sign indication byte.
     * @param value - any big integer number. A `null`-value will return `null`
     * @return A byte array without a leading zero byte if present in the signed encoding.
     * BigInteger.ZERO will return an array with length 1 and byte-value 0.
     */
    private fun BigInteger?.toBytes(): ByteArray? {
        if (this == null)
            return null

        var data = this.toByteArray()

        if (data.size != 1 && data[0].toInt() == 0) {
            val tmp = ByteArray(data.size - 1)
            System.arraycopy(data, 1, tmp, 0, tmp.size)
            data = tmp
        }
        return data
    }

    /**
     * Prints a value with the tag
     */
    private fun logD(value: String) {
        Log.d(WEB3J_ETH_EXAMPLE_KEY, value)
    }

    /**
     * Prints a value with the tag
     */
    companion object {
        private const val PREFIX = "0x"

        private const val CRYPTO_API_KEY = "YOUR_CRYPTO_API_KEY"
        private const val ETH_ADDRESS_1 = "SENDER_ADDRESS"
        private const val ETH_ADDRESS_2 = "RECIPIENT_ADDRESS"

        private const val CALL_TIMEOUT = 30000L
        private const val READ_TIMEOUT = 30000L
        private const val CONNECT_TIMEOUT = 15000L
        private const val SEND_AMOUNT = 10000000L

        private const val WEB3J_ETH_EXAMPLE_KEY = "ETH_LOG"
    }
}
