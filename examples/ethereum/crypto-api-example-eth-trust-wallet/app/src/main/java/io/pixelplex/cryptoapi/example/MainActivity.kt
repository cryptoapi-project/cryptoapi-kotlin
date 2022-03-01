package io.pixelplex.cryptoapi.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.protobuf.ByteString
import io.pixelplex.cryptoapi.example.util.Constants
import io.pixelplex.cryptoapi.example.util.toByteString
import io.pixelplex.cryptoapi.example.util.toHexString
import io.pixelplex.cryptoapi.example.util.withPrefix
import io.pixelplex.mobile.cryptoapi.CryptoApiFramework
import io.pixelplex.mobile.cryptoapi.core.CryptoApi.URL.TESTNET
import io.pixelplex.mobile.cryptoapi.model.data.eth.EthEstimatedGas
import io.pixelplex.mobile.cryptoapi.model.data.eth.EthEstimatedGasCall
import io.pixelplex.mobile.cryptoapi.model.data.eth.EthTransactionRawCall
import io.pixelplex.mobile.cryptoapi.wrapper.CryptoApiConfiguration
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.spongycastle.util.encoders.Hex
import wallet.core.java.AnySigner
import wallet.core.jni.CoinType
import wallet.core.jni.HDWallet
import wallet.core.jni.PrivateKey
import wallet.core.jni.proto.Ethereum
import java.math.BigInteger

class MainActivity : AppCompatActivity() {

    init {
        System.loadLibrary("TrustWalletCore")
    }

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
    *   Receiver's address
    */
    private val toAddress = Constants.ETH_TO_ADDRESS

    /*
    *   Contract's address
    */
    private val contractAddress = Constants.ETH_CONTRACT_ADDRESS

    /*
    *   Send amount
    */
    private val sendAmountInWei = BigInteger.valueOf(Constants.SEND_AMOUNT_IN_WEI)

    /*
    *   Coin type for Ethereum
    */
    private val coinType = CoinType.ETHEREUM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            /*
                Creating or Importing a Multi-Coin HD Wallet

                val wallet = HDWallet(128, Constants.PASSPHRASE)
                logD("mnemonic = ${wallet.mnemonic()}")

                val wallet = HDWallet(mnemonic, passphrase)

            */

            val wallet = HDWallet(Constants.MNEMONIC, Constants.PASSPHRASE)
            val ethAddress = wallet.getAddressForCoin(coinType)
            val secretPrivateKey = wallet.getKeyForCoin(coinType)

            // Get gas estimation
            val estimatedGasBody = EthEstimatedGasCall(
                from = ethAddress,
                to = toAddress,
                value = Hex.toHexString(sendAmountInWei.toByteArray())
                    .withPrefix(Constants.HEX_PREFIX)
            )
            val estimatedGasResponse = apiClient.estimateGas(estimatedGasBody)

            // Create signed transaction
            val signerInput = createTransactionSignerInput(
                estimatedGasResponse,
                secretPrivateKey,
                toAddress,
                sendAmountInWei
            )
            val signerOutput = AnySigner.sign(
                signerInput,
                coinType,
                Ethereum.SigningOutput.parser()
            )
            val txHash = signerOutput.encoded.toByteArray().toHexString(true)
            logD("tx = $txHash")

            // Send raw transaction
            val result = apiClient.sendRawTransaction(EthTransactionRawCall(txHash))
            logD("txId = $result")

            // Create a signed transaction that will be used to get gas estimation for the token
            var signerTokenInput = createTokenSignerInput(
                estimatedGasResponse,
                secretPrivateKey,
                toAddress,
                contractAddress,
                sendAmountInWei
            )
            var signerTokenOutput = AnySigner.sign(
                signerInput,
                coinType,
                Ethereum.SigningOutput.parser()
            )
            var txTokenHash = signerOutput.encoded.toByteArray().toHexString(true)

            // Get a new gas estimation for the token
            val estimatedGasBodyToken = estimatedGasBody.copy(data = txTokenHash)
            val estimatedGasTokenResponse = apiClient.estimateGas(estimatedGasBodyToken)

            // Create a new transaction with new gas estimation
            signerTokenInput = createTokenSignerInput(
                estimatedGasTokenResponse,
                secretPrivateKey,
                toAddress,
                contractAddress,
                sendAmountInWei
            )
            signerTokenOutput = AnySigner.sign(
                signerTokenInput,
                coinType,
                Ethereum.SigningOutput.parser()
            )
            txTokenHash = signerTokenOutput.encoded.toByteArray().toHexString(true)
            logD("txToken = $txTokenHash")

            // Send raw transaction
            val resultToken = apiClient.sendRawTransaction(EthTransactionRawCall(txTokenHash))
            logD("txId = $resultToken")
        }
    }

    private fun createTransactionSignerInput(
        estimatedGas: EthEstimatedGas,
        privateKey: PrivateKey,
        toAddress: String,
        amount: BigInteger
    ): Ethereum.SigningInput {
        return createSignerInputBuilder(
            estimatedGas,
            privateKey,
            toAddress
        ).apply {
            this.transaction = Ethereum.Transaction.newBuilder().apply {
                this.transfer = Ethereum.Transaction.Transfer.newBuilder().apply {
                    this.amount = amount.toByteString()
                }.build()
            }.build()
        }.build()
    }

    private fun createTokenSignerInput(
        estimatedGas: EthEstimatedGas,
        privateKey: PrivateKey,
        toAddress: String,
        contractAddress: String,
        amount: BigInteger
    ): Ethereum.SigningInput {
        return createSignerInputBuilder(
            estimatedGas,
            privateKey,
            contractAddress
        ).apply {
            this.transaction = Ethereum.Transaction.newBuilder().apply {
                this.erc20Transfer = Ethereum.Transaction.ERC20Transfer.newBuilder().apply {
                    this.amount = amount.toByteString()
                    this.to = toAddress
                }.build()
            }.build()
        }.build()
    }

    private fun createSignerInputBuilder(
        estimatedGas: EthEstimatedGas,
        privateKey: PrivateKey,
        toAddress: String,
    ): Ethereum.SigningInput.Builder {
        return Ethereum.SigningInput.newBuilder().apply {
            this.chainId = Constants.ETH_CHAIN_ID.toBigInteger().toByteString()
            this.nonce = estimatedGas.nonce.toBigInteger().toByteString()
            this.gasPrice = estimatedGas.gasPrice.toBigInteger().toByteString()
            this.gasLimit = estimatedGas.estimateGas.toBigInteger().toByteString()
            this.toAddress = toAddress
            this.privateKey = ByteString.copyFrom(privateKey.data())
        }
    }

    private fun logD(value: String) {
        Log.d(Constants.LOG_TAG, value)
    }

}