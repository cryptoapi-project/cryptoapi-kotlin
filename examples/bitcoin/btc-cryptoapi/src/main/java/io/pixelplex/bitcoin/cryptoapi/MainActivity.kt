package io.pixelplex.bitcoin.cryptoapi

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.pixelplex.cryptoapi_android_framework.CryptoApiFramework
import io.pixelplex.cryptoapi_android_framework.core.CryptoApi
import io.pixelplex.model.data.btc.BtcOutput
import io.pixelplex.model.data.btc.BtcOutputStatus
import io.pixelplex.model.data.btc.BtcRawTransaction
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.bitcoinj.core.Address
import org.bitcoinj.core.Coin
import org.bitcoinj.core.Context
import org.bitcoinj.core.ECKey
import org.bitcoinj.core.NetworkParameters
import org.bitcoinj.core.Sha256Hash
import org.bitcoinj.core.Transaction
import org.bitcoinj.core.TransactionConfidence
import org.bitcoinj.core.TransactionOutPoint
import org.bitcoinj.core.Utils
import org.bitcoinj.crypto.ChildNumber
import org.bitcoinj.crypto.DeterministicHierarchy
import org.bitcoinj.crypto.DeterministicKey
import org.bitcoinj.params.TestNet3Params
import org.bitcoinj.script.Script
import org.bitcoinj.wallet.DeterministicSeed
import org.bitcoinj.wallet.KeyChainGroup
import org.spongycastle.util.encoders.Hex
import java.math.BigInteger

class MainActivity : AppCompatActivity() {

    private val apiClient by lazy {
        CryptoApiFramework.getInstance(
            CALL_TIMEOUT,
            CONNECT_TIMEOUT,
            READ_TIMEOUT,
            CRYPTO_API_KEY,
            CryptoApi.URL.TESTNET
        ).bitcoinAsyncApi
    }

    private val networkParams by lazy {
        TestNet3Params.get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            //init bitcoinJ
            Context.getOrCreate(networkParams)

            // MARK: Get outputs
            // get address unspent outputs to calculate balance or build the transaction
            val outputs = apiClient.getOutputs(BtcOutputStatus.unspent, listOf(FROM_ADDRESS))
            printLog(outputs.toString())

            // MARK: Build transaction
            // prepare values for transaction
            val fromAddress = FROM_ADDRESS
            val toAddress = TO_ADDRESS
            val amountWithWei = AMOUNT.toBigInteger()
            val fee = 1000.toBigInteger()
            val privateKey = getPrivateKey(BTC_MNEMONIC)

            //create signed transaction
            val signedTransaction = createSignedTransaction(
                fromAddress, toAddress, amountWithWei, fee, Hex.decode(privateKey), outputs
            )

            // get a transaction hex and send it with CryptoApi
            val transactionHex = Hex.toHexString(signedTransaction.unsafeBitcoinSerialize())
            try {
                apiClient.sendRawTransaction(BtcRawTransaction(transactionHex))
                printLog("Send success")
            } catch (ex: Exception) {
                printLog("Send failure")
            }

            // MARK: Fee estimating
            // response has result like "0.00001". Convert it to satoshi if necessary.
            val estimatedFeeResponse = apiClient.estimateFee()
            //firs of all, you need to get fee rate for kilobyte
            val feePerKb = estimatedFeeResponse.toBigDecimal().multiply(1000000.toBigDecimal()).toBigInteger()

            // we need to calculate how much the transaction weighs and how many outs we need to take in transaction
            // to cover the amount sent and the fee.
            var txSize = Transaction(networkParams).messageSize //build transaction like example above and get size

            var estimatedFee = BigInteger.ZERO
            while (txSize > 0) {
                txSize -= KB_VALUE
                estimatedFee += feePerKb
            }

            // estimatedFee is the result of estimation of fee
            printLog("estimated fee = $fee")
        }
    }

    private fun getPrivateKey(mnemonic: String): String {
        val seed = DeterministicSeed(
            mnemonic,
            null,
            "",
            DeterministicHierarchy.BIP32_STANDARDISATION_TIME_SECS.toLong()
        )
        val pathParent = arrayListOf<ChildNumber>().apply {
            add(ChildNumber(44, true))
            add(ChildNumber(TESTNET_DERIVATION_PART, true))
            add(ChildNumber(0, true))
            add(ChildNumber(0, false))
            add(ChildNumber(0, false))
        }

        val keyChainGroup = KeyChainGroup(networkParams, seed)

        val key = keyChainGroup.activeKeyChain!!.getKeyByPath(pathParent, true)
        return Hex.toHexString(key.privKeyBytes)
    }

    private fun createSignedTransaction(
        fromAddress: String,
        toAddress: String,
        amount: BigInteger,
        fee: BigInteger,
        privateKey: ByteArray,
        unspent: List<BtcOutput>
    ): Transaction {
        val transaction = Transaction(networkParams)
        val to = Address.fromBase58(networkParams, toAddress)

        transaction.addOutput(Coin.valueOf(amount.toLong()), to)

        // 2) Add fee to sending amount
        val amountWithFee = amount.add(fee)

        // 3) Total unspent balance
        val totalAmount =
            calculateTotalAmount(amountWithFee, unspent.sortedBy { it.value.toLong() })

        // 4) Add change to output (change from transaction amount + fee)
        val change = totalAmount.subtract(amountWithFee)

        if (change.toLong() != 0L) {
            transaction.addOutput(
                Coin.valueOf(change.toLong()),
                Address.fromBase58(networkParams, fromAddress)
            )
        }

        val signKey = DeterministicKey.fromPrivate(privateKey)
        computeChangeAmount(unspent, transaction, signKey, networkParams, amountWithFee)

        transaction.confidence.source = TransactionConfidence.Source.SELF
        transaction.purpose = Transaction.Purpose.USER_PAYMENT

        return transaction
    }

    private fun calculateTotalAmount(
        requiredAmount: BigInteger,
        unspent: List<BtcOutput>
    ): BigInteger {
        var totalAmount = BigInteger.ZERO
        for (unspentOutput in unspent) {
            if (unspentOutput.script.isNotBlank()) {
                totalAmount = totalAmount.add(unspentOutput.value)
                if (totalAmount.toLong() >= requiredAmount.toLong()) {
                    break
                }
            }
        }

        return totalAmount
    }

    private fun computeChangeAmount(
        unspentOutputs: List<BtcOutput>,
        transaction: Transaction,
        key: ECKey,
        currentNetParams: NetworkParameters,
        amount: BigInteger
    ): BigInteger? {
        var amountFromOutput: BigInteger? = BigInteger.ZERO
        for (unspentOutput in unspentOutputs) {
            amountFromOutput =
                processOutput(unspentOutput, currentNetParams, transaction, key, amountFromOutput)

            if (amountFromOutput != null && amountFromOutput.toLong() >= amount.toLong()) break
        }
        return amountFromOutput
    }

    private fun processOutput(
        unspentOutput: BtcOutput,
        currentNetParams: NetworkParameters,
        transaction: Transaction,
        key: ECKey,
        amountFromOutput: BigInteger?
    ): BigInteger? {
        if (unspentOutput.value.toLong() != 0L) {
            val sha256Hash = Sha256Hash.wrap(Utils.parseAsHexOrBase58(unspentOutput.mintTansactionHash))

            if (unspentOutput.script.isNotBlank()) {
                val outPoint = TransactionOutPoint(
                    currentNetParams,
                    unspentOutput.mintIndex.toLong(),
                    sha256Hash
                )
                val script = Script(Utils.parseAsHexOrBase58(unspentOutput.script))
                transaction.addSignedInput(
                    outPoint,
                    script,
                    key,
                    Transaction.SigHash.ALL,
                    true
                )
                return amountFromOutput!!.add(unspentOutput.value)
            }
        }

        return amountFromOutput
    }

    private fun printLog(message: String) {
        Log.d(CRYPTO_API_BTC_LOG_KEY, message)
    }

    companion object {
        private const val CRYPTO_API_BTC_LOG_KEY = "BTC_CRYPTO_API_LOG"

        private const val CALL_TIMEOUT = 30000L
        private const val READ_TIMEOUT = 30000L
        private const val CONNECT_TIMEOUT = 15000L
        private const val CRYPTO_API_KEY = "5de552d7efc6ff2e1b09d946cc5263e346003a93ab28bf2ffeb24979da85a1f5"

        private const val BTC_MNEMONIC = "eight dish palace wrist place spatial assault hello inhale bridge hundred twelve"

        private const val FROM_ADDRESS = "moy1v5Xp2BN8rMqCAeXB6kQK2E3ArxgyuZ"
        private const val TO_ADDRESS = "mhqAmxkzxpjkz2Te2V1LmD6ETKoBXkz3eW"

        private const val AMOUNT = 1000

        private const val MAINNET_DERIVATION_PART = 0
        private const val TESTNET_DERIVATION_PART = 1

        private const val KB_VALUE = 1000
    }

}
