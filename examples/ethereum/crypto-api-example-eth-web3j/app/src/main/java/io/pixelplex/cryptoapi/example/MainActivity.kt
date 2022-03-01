package io.pixelplex.cryptoapi.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.pixelplex.cryptoapi.example.model.ContractInputEncoder
import io.pixelplex.cryptoapi.example.models.EthContractAddressInputValueType
import io.pixelplex.cryptoapi.example.models.InputValue
import io.pixelplex.cryptoapi.example.models.NumberInputValueType
import io.pixelplex.cryptoapi.example.util.Constants
import io.pixelplex.mobile.cryptoapi.CryptoApiFramework
import io.pixelplex.mobile.cryptoapi.core.CryptoApi.URL.TESTNET
import io.pixelplex.mobile.cryptoapi.model.data.eth.EthContractBytecodeResponse
import io.pixelplex.mobile.cryptoapi.model.data.eth.EthContractCall
import io.pixelplex.mobile.cryptoapi.model.data.eth.EthEstimatedGas
import io.pixelplex.mobile.cryptoapi.model.data.eth.EthEstimatedGasCall
import io.pixelplex.mobile.cryptoapi.model.data.eth.EthTransactionRawCall
import io.pixelplex.mobile.cryptoapi.wrapper.CryptoApiConfiguration
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.spongycastle.util.encoders.Hex
import org.web3j.crypto.ContractUtils
import org.web3j.crypto.Credentials
import org.web3j.crypto.ECKeyPair
import org.web3j.crypto.RawTransaction
import org.web3j.crypto.TransactionEncoder
import org.web3j.ens.Contracts
import org.web3j.protocol.Web3jFactory
import org.web3j.protocol.core.methods.response.EthEstimateGas
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.tx.Contract
import org.web3j.tx.Transfer
import org.web3j.tx.response.TransactionReceiptProcessor
import org.web3j.utils.Numeric
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
            url = TESTNET
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

    private val privateKey = ECKeyPair.create(Numeric.hexStringToByteArray("0x38edab5422abd038154d221a8aa4bb888a471c62cc8176f4c4cfd15446c47726"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       GlobalScope.launch {

           /*
            *   Creating and sending ethereum transaction with Web3j
            */

           // Estimation gas obtaining
           val estimationGas = apiClient.estimateGas(estimatedGas)
           logD(estimationGas.toString())

           // Raw transaction object creating
           val tx = createSignedTransaction(
               estimationGas,
               toAddress,
               sendAmountBigInt
           )
           logD("raw tx = $tx")

           // Raw transaction Sending
           //val result = apiClient.sendRawTransaction(EthTransactionRawCall(tx))
           //logD(result)

           val contractData = getContractBytecode(contractAddress, sendAmountBigInt)

           var txContract = createSignedContractTransaction(
               estimationGas,
               contractData,
               sendAmountBigInt
           )

           // Estimation gas obtaining for the token
           val estimationGasForToken = apiClient.estimateGas(estimatedGas.copy(data = txContract))
           logD(estimationGasForToken.toString())

           txContract = createSignedContractTransaction(
               estimationGasForToken,
               contractData,
               sendAmountBigInt
           )
           logD("raw tx = $txContract")

           // Raw transaction Sending
           val result = apiClient.sendRawTransaction(EthTransactionRawCall(txContract))
           logD(result)
       }

    }

    private fun createSignedTransaction(
        estimationGas: EthEstimatedGas,
        toAddress: String,
        amount: BigInteger
    ): String {
        val rawTransaction = RawTransaction.createEtherTransaction(
            BigInteger.valueOf(estimationGas.nonce.toLong()),
            BigInteger.valueOf(estimationGas.gasPrice),
            BigInteger.valueOf(estimationGas.estimateGas),
            toAddress,
            amount
        )
        val signedMessage = TransactionEncoder.signMessage(
            rawTransaction,
            Constants.ETH_CHAIN_ID.toByte(),
            Credentials.create(privateKey)
        )
        return Numeric.toHexString(signedMessage)
    }

    private fun createSignedContractTransaction(
        estimationGas: EthEstimatedGas,
        contractData: String,
        amount: BigInteger
    ): String {
        val rawTransaction = RawTransaction.createContractTransaction(
            BigInteger.valueOf(estimationGas.nonce.toLong()),
            BigInteger.valueOf(estimationGas.gasPrice),
            BigInteger.valueOf(estimationGas.estimateGas),
            amount,
            contractData
        )
        val signedMessage = TransactionEncoder.signMessage(
            rawTransaction,
            Constants.ETH_CHAIN_ID.toByte(),
            Credentials.create(privateKey)
        )
        return Numeric.toHexString(signedMessage)
    }

    fun getContractBytecode(toAddress: String, amountWithWei: BigInteger): String {
        val byteCode = ContractInputEncoder().encode(
            "transfer", listOf(
                InputValue(
                    EthContractAddressInputValueType("address"),
                    toAddress
                ), InputValue(
                    NumberInputValueType("uint256"), amountWithWei.toString()
                )
            )
        )
        return byteCode
    }

    private fun logD(value: String) {
        Log.d(Constants.LOG_TAG, value)
    }

}