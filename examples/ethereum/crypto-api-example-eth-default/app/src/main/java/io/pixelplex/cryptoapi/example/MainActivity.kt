package io.pixelplex.cryptoapi.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.pixelplex.cryptoapi.example.models.EthTransactionBuilder
import io.pixelplex.cryptoapi.example.util.Constants
import io.pixelplex.mobile.cryptoapi.CryptoApiFramework
import io.pixelplex.mobile.cryptoapi.core.CryptoApi
import io.pixelplex.mobile.cryptoapi.example.R
import io.pixelplex.mobile.cryptoapi.model.data.eth.EthEstimatedGas
import io.pixelplex.mobile.cryptoapi.model.data.eth.EthEstimatedGasCall
import io.pixelplex.mobile.cryptoapi.model.data.eth.EthTransactionRawCall
import io.pixelplex.mobile.cryptoapi.wrapper.CryptoApiConfiguration
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.bouncycastle.util.encoders.Hex
import java.math.BigInteger

class MainActivity : AppCompatActivity() {

    /*
    * Crypto API libarary initializing
    */
    private val apiClient by lazy {
        val configuration = CryptoApiConfiguration(
            authorizationToken = Constants.CRYPTO_API_TOKEN,
            callTimeout = Constants.REQUEST_CALL_TIMEOUT,
            connectTimeout = Constants.REQUEST_CONNECT_TIMEOUT,
            readTimeOut = Constants.REQUEST_READ_TIMEOUT,
            url = CryptoApi.URL.TESTNET
        )
        CryptoApiFramework.getInstance(configuration).ethereumAsyncApi
    }

    /*
    * Sender's address
    */
    private val fromAddress = Constants.ETH_FROM_ADDRESS

    /*
    * Receiver's address
    */
    private val toAddress = Constants.ETH_TO_ADDRESS

    /*
    * Smart contract's address
    */
    private val contractAddress = Constants.ETH_CONTRACT_ADDRESS

    /*
    * Estimation gas object initializing
    */
    private val estimatedGas = EthEstimatedGasCall(
        from = fromAddress,
        to = toAddress,
        value = "10"
    )

    /*
    * Send amount Big integer object initializing
    */
    private val sendAmountBigInt = BigInteger.valueOf(Constants.SEND_AMOUNT_WEI)

    /*
    * Your private key byte array
    */

    private val privateKey = Hex.decode("38edab5422abd038154d221a8aa4bb888a471c62cc8176f4c4cfd15446c47726")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            /*
            *   Creating and sending ethereum transaction
            */

            // Estimation gas obtaining
            val estimationGas = apiClient.estimateGas(estimatedGas)
            logD(estimationGas.toString())

            // Transaction initialization
            val rawTx = createSignedTransaction(
                estimationGas,
                sendAmountBigInt,
                toAddress,
                privateKey
            )
            logD("tx raw $rawTx")

            // Sending raw transaction
            //val txHash = apiClient.sendRawTransaction(EthTransactionRawCall(rawTx))
            //logD(txHash)

            /*
            *   Creating and sending ethereum token
            */

            // Creating data of contract
            val contractData = EthTransactionBuilder.getContractBytecode(
                toAddress,
                sendAmountBigInt
            )

            /*
            *   Warning!
            *   Before sending the token we need to get a new gas estimation for the token
            */

            // Create a transaction that will be used to get gas estimation for the token
            var rawTokenTx = createSignedTransaction(
                estimationGas,
                null,
                contractAddress,
                privateKey,
                contractData
            )

            // Estimation gas obtaining for the token
            val estimationGasForToken = apiClient.estimateGas(estimatedGas.copy(data = rawTokenTx))
            logD(estimationGasForToken.toString())

            // Update the token transaction with new gas estimation
            rawTokenTx = createSignedTransaction(
                estimationGasForToken,
                null,
                contractAddress,
                privateKey,
                contractData
            )
            logD("token tx raw $rawTokenTx")

            //Sending raw transaction for token
            val txHashForToken = apiClient.sendRawTransaction(EthTransactionRawCall(rawTokenTx))
            logD(txHashForToken)
        }
    }

    private fun createSignedTransaction(
        estimationGas: EthEstimatedGas,
        amount: BigInteger?,
        toAddress: String,
        privateKey: ByteArray,
        contractData: String? = null
    ): String {
        return EthTransactionBuilder.createSignedTransaction(
            BigInteger.valueOf(estimationGas.nonce.toLong()),
            BigInteger.valueOf(estimationGas.gasPrice),
            BigInteger.valueOf(estimationGas.estimateGas),
            toAddress,
            amount,
            privateKey,
            Constants.ETH_CHAIN_ID,
            contractData
        )
    }

    private fun logD(value: String) {
        Log.d(Constants.LOG_TAG, value)
    }
}