package io.pixelplex.cryptoapi.example

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.pixelplex.cryptoapi.example.util.Constants
import io.pixelplex.cryptoapi.example.util.btcToSatoshi
import io.pixelplex.mobile.cryptoapi.CryptoApiFramework
import io.pixelplex.mobile.cryptoapi.core.CryptoApi
import io.pixelplex.mobile.cryptoapi.model.data.btc.BtcOutput
import io.pixelplex.mobile.cryptoapi.model.data.btc.BtcOutputStatus
import io.pixelplex.mobile.cryptoapi.model.data.btc.BtcRawTransaction
import io.pixelplex.mobile.cryptoapi.wrapper.CryptoApiConfiguration
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.bitcoinj.core.Address
import org.bitcoinj.core.Coin
import org.bitcoinj.core.Context
import org.bitcoinj.core.ECKey
import org.bitcoinj.core.Sha256Hash
import org.bitcoinj.core.Transaction
import org.bitcoinj.core.Transaction.SigHash
import org.bitcoinj.core.TransactionConfidence
import org.bitcoinj.core.TransactionInput
import org.bitcoinj.core.TransactionOutPoint
import org.bitcoinj.core.TransactionWitness
import org.bitcoinj.params.TestNet3Params
import org.bitcoinj.script.Script
import org.bitcoinj.script.ScriptBuilder
import org.bitcoinj.script.ScriptError.SCRIPT_ERR_UNKNOWN_ERROR
import org.bitcoinj.script.ScriptException
import org.bitcoinj.script.ScriptPattern
import org.bouncycastle.util.encoders.Hex
import java.math.BigDecimal
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
        CryptoApiFramework.getInstance(configuration).bitcoinAsyncApi
    }

    /*
     *  Represents BTC network
     */
    private val networkParams by lazy {
        TestNet3Params.get()
    }

    /*
    *   Sender's address
    */
    private val fromAddress = Constants.BTC_FROM_ADDRESS

    /*
    *   Receiver's address
    */
    private val toAddress = Constants.BTC_TO_ADDRESS

    /*
    *   Send amount
    */
    private val sendAmountSatoshi = BigInteger.valueOf(Constants.SEND_AMOUNT_SATOSHI)

    /*
    *   Your private key byte array
    */
    private val privateKey = ByteArray(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {

            // Init bitcoinJ
            Context.getOrCreate(networkParams)

            val signKey = ECKey.fromPrivate(privateKey)

            // Get outputs of previous transaction
            val outputs = try {
                apiClient.getOutputs(BtcOutputStatus.unspent, listOf(fromAddress))
            } catch (ex: Exception) {
                emptyList()
            }
            logD(outputs.toString())

            // Get fee estimation (coin/Kb) and convert to (satoshi/byte)
            val estimatedFeePerByte = btcToSatoshi(apiClient.estimateFee()
                .toBigDecimal())
                .div(BigDecimal(Constants.BYTES_IN_KB))
                .toBigInteger()

            // Create signed transaction wit default fee
            val transaction = createSignedTransaction(
                fromAddress,
                toAddress,
                sendAmountSatoshi,
                Constants.DEFAULT_FEE.toBigInteger(),
                signKey,
                outputs
            )

            /*
                Now we have to calculate fee for the created transaction and create a new transaction
                with the new fee value
            */
            val fee = estimatedFeePerByte.multiply(transaction.vsize.toBigInteger())

            val resultTransaction = createSignedTransaction(
                fromAddress,
                toAddress,
                sendAmountSatoshi,
                fee,
                signKey,
                outputs
            )

            // Get a transaction hex and send it with CryptoApi
            val transactionHex = resultTransaction.toHexString()
            logD("tx hash = is $transactionHex")

            // Send the transaction via CryptoApiLib
            try {
                val result = apiClient.sendRawTransaction(BtcRawTransaction(transactionHex))
                logD("txId = ${result.result}")
            } catch (ex: Exception) {
                // Request failed
            }
        }
    }

    private fun createSignedTransaction(
        fromAddress: String,
        toAddress: String,
        amount: BigInteger,
        fee: BigInteger,
        privateKey: ECKey,
        unspent: List<BtcOutput>
    ): Transaction {
        val transaction = Transaction(networkParams)

        // Create an output with the amount to be sent
        transaction.addOutput(
            Coin.valueOf(amount.toLong()),
            Address.fromString(networkParams, toAddress)
        )

        // Calculate total amount and change value
        val amountWithFee = amount.add(fee)
        val totalAmount = unspent.sumOf { it.value }
        val change = totalAmount.subtract(amountWithFee)

        // Create an output for change value
        if (change.toLong() > 0L) {
            transaction.addOutput(
                Coin.valueOf(change.toLong()),
                Address.fromString(networkParams, fromAddress)
            )
        }

        unspent.forEach {
            val outPoint = TransactionOutPoint(
                networkParams,
                it.mintIndex.toLong(),
                Sha256Hash.wrap(it.mintTansactionHash)
            )
            transaction.addSignedInput(
                outPoint,
                Script(Hex.decode(it.script)),
                Coin.valueOf(it.value.toLong()),
                privateKey,
                SigHash.ALL,
                false
            )
        }

        transaction.confidence.source = TransactionConfidence.Source.SELF
        transaction.purpose = Transaction.Purpose.USER_PAYMENT

        return transaction
    }

    fun Transaction.addSignedInput(
        prevOut: TransactionOutPoint?, scriptPubKey: Script, amount: Coin?, sigKey: ECKey,
        sigHash: SigHash, anyoneCanPay: Boolean = false
    ): TransactionInput? {

        if (amount == null || amount.value <= 0) {
            logD("Illegal amount value. Amount is required for SegWit transactions.")
            return null
        }
        val input = TransactionInput(networkParams, this, byteArrayOf(), prevOut, amount)
        this.addInput(input)
        val inputIndex = this.inputs.size - 1
        when {
            ScriptPattern.isP2PK(scriptPubKey) -> {
                val signature = this.calculateSignature(
                    inputIndex, sigKey, scriptPubKey, sigHash,
                    anyoneCanPay
                )
                input.scriptSig = ScriptBuilder.createInputScript(signature)
                input.witness = null
            }
            ScriptPattern.isP2PKH(scriptPubKey) -> {
                val signature = this.calculateSignature(
                    inputIndex, sigKey, scriptPubKey, sigHash,
                    anyoneCanPay
                )
                input.scriptSig = ScriptBuilder.createInputScript(signature, sigKey)
                input.witness = null
            }
            ScriptPattern.isP2WPKH(scriptPubKey) -> {
                val scriptCode = ScriptBuilder.createP2PKHOutputScript(sigKey)
                val signature = this.calculateWitnessSignature(
                    inputIndex, sigKey, scriptCode, input.value,
                    sigHash, anyoneCanPay
                )
                input.scriptSig = ScriptBuilder.createEmpty()
                input.witness = TransactionWitness.redeemP2WPKH(signature, sigKey)
            }
            else -> {
                throw ScriptException(
                    SCRIPT_ERR_UNKNOWN_ERROR,
                    "Don't know how to sign for this kind of scriptPubKey: $scriptPubKey"
                )
            }
        }
        return input
    }

    private fun logD(value: String) {
        Log.d(Constants.LOG_TAG, value)
    }

}