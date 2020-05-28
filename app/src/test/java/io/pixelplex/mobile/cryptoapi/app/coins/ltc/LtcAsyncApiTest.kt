package io.pixelplex.mobile.cryptoapi.app.coins.ltc

import io.pixelplex.mobile.cryptoapi.CryptoApiFramework
import io.pixelplex.mobile.cryptoapi.app.BuildConfig
import io.pixelplex.mobile.cryptoapi.core.CryptoApi
import io.pixelplex.mobile.cryptoapi.model.data.btc.BtcOutputStatus
import io.pixelplex.mobile.cryptoapi.model.data.btc.BtcRawTransaction
import io.pixelplex.mobile.cryptoapi.wrapper.CryptoApiConfiguration
import kotlinx.coroutines.runBlocking
import org.bitcoinj.core.*
import org.bitcoinj.crypto.ChildNumber
import org.bitcoinj.crypto.DeterministicHierarchy
import org.bitcoinj.crypto.DeterministicKey
import org.bitcoinj.params.MainNetParams
import org.bitcoinj.params.TestNet3Params
import org.bitcoinj.script.Script
import org.bitcoinj.wallet.DeterministicSeed
import org.bitcoinj.wallet.KeyChainGroup
import org.junit.Assert
import org.junit.Test
import org.spongycastle.util.encoders.Hex
import java.math.BigDecimal
import java.math.BigInteger

class LtcAsyncApiTest {
    private val apiClient = CryptoApiFramework.getInstance(
        CryptoApiConfiguration(
            url = CryptoApi.URL.TESTNET,
            authorizationToken = BuildConfig.CRYPTO_API_KEY
        )
    ).litecoinAsyncApi

    private val networkParameters by lazy {
        TestNet3Params.get()
    }

    @Test
    fun getNetwork() = runBlocking {
        try {
            apiClient.getNetwork().let { resp ->
                Assert.assertTrue(resp.lastBlock > BigInteger.ZERO)
            }
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun getBlock() = runBlocking {
        try {
            val resp = apiClient.getBlock(TestValues.BLOCK_NUMBER)
            Assert.assertTrue(resp.hash.isNotEmpty())
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun getBlocks() = runBlocking {
        try {
            val resp = apiClient.getBlocks()
            Assert.assertTrue(resp.items.isNotEmpty())
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun getTransaction() = runBlocking {
        try {
            val resp = apiClient.getTransaction(TestValues.TRANSACTION_HASH)
            Assert.assertTrue(resp.blockHash.isNotEmpty())
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun getTransactions() = runBlocking {
        try {
            val resp = apiClient.getTransactions(
                TestValues.BLOCK_NUMBER,
                TestValues.LTC_FROM,
                TestValues.LTC_TO
            )
            Assert.assertTrue(resp.items.isNotEmpty())
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun getTransactionsByAddresses() = runBlocking {
        try {
            val resp = apiClient.getTransactions(
                listOf(
                    TestValues.LTC_FROM,
                    TestValues.LTC_TO
                )
            )
            Assert.assertTrue(resp.items.isNotEmpty())
        } catch (e: Exception) {
            Assert.fail()
        }
    }

// TODO: uncomment to send a transactin. Currently the from address has no any funds quite.
//    @Test
//    fun sendRawTransaction() = runBlocking {
//        try {
//            val approximateFee = approximateFee(TestValues.LTC_FROM, 100000.toBigDecimal()).toSafeBigDecimal()
//            val fee = approximateFee.setScale(8).toBigInteger()
//            val amountWithWei = 100000.toBigDecimal().setScale(8).toBigInteger()
//
//            val response = try {
//                apiClient.getOutputs(BtcOutputStatus.unspent, listOf(TestValues.LTC_FROM))
//            } catch (ex: Exception) {
//                throw Exception()
//            }
//
//            val unspent = response.map { body ->
//                UnspentOutput(
//                    TestValues.LTC_FROM,
//                    txid = body.mintTansactionHash,
//                    outputIndex = body.mintIndex,
//                    script = body.script,
//                    height = body.mintBlockHeight,
//                    satoshis = body.value
//                )
//            }
//
//            val transaction = createBtcTransaction(
//                TestValues.LTC_FROM,
//                TestValues.LTC_TO,
//                amountWithWei,
//                fee,
//                generatePrivateKey(TestValues.MNEMONIC),
//                unspent
//            )
//
//            val signedTransaction = Hex.toHexString(transaction.unsafeBitcoinSerialize())
//
//            val resp = apiClient.sendRawTransaction(
//                BtcRawTransaction(
//                    signedTransaction
//                )
//            )
//
//            Assert.assertTrue(resp.result.isNotEmpty())
//        } catch (e: Exception) {
//            Assert.fail()
//        }
//    }

    @Test
    fun getOutputs() = runBlocking {
        try {
            val resp =
                apiClient.getOutputs(
                    BtcOutputStatus.spent,
                    listOf(
                        TestValues.LTC_FROM,
                        TestValues.LTC_TO
                    )
                )
            Assert.assertTrue(resp.isNotEmpty())
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun getAddressesWithBalances() = runBlocking {
        try {
            val resp =
                apiClient.getAddressesWithBalances(
                    listOf(
                        TestValues.LTC_FROM,
                        TestValues.LTC_TO
                    )
                )
            Assert.assertTrue(resp.isNotEmpty())
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun estimateFee() = runBlocking {
        try {
            val resp = apiClient.estimateFee()
            Assert.assertTrue(resp.isNotEmpty())
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    private suspend fun approximateFee(address: String, amount: BigDecimal): String {
        val (source, private) = generateFakeTransactionSource()
        val value = amount.setScale(8).toBigInteger()

        val feeValue =
            TestValues.DEFAULT_FEE_FOR_KB_VALUE.toSafeBigDecimal().setScale(8)
                .toBigInteger()

        val response = try {
            apiClient.getOutputs(BtcOutputStatus.unspent, listOf(address))
        } catch (ex: Exception) {
            throw Exception()
        }

        var size = try {
            transactionSize(
                source,
                address,
                value,
                feeValue,
                private,
                response.map { body ->
                    UnspentOutput(
                        address,
                        txid = body.mintTansactionHash,
                        outputIndex = body.mintIndex,
                        script = body.script,
                        height = body.mintBlockHeight,
                        satoshis = body.value
                    )
                }
            )
        } catch (exception: Exception) {
            throw Exception()
        }

        var fee = BigInteger.ZERO

        while (size > 0) {
            size -= TestValues.KB_VALUE
            fee += feeValue
        }

        return fee.toString()
    }

    private fun transactionSize(
        fromAddress: String,
        receiverAddress: String,
        value: BigInteger,
        fee: BigInteger,
        privateKey: String,
        unspent: List<UnspentOutput>
    ): Int {
        val transaction = createBtcTransaction(
            fromAddress,
            receiverAddress,
            value,
            fee,
            Hex.decode(privateKey),
            unspent
        )

        return transaction.messageSize
    }

    private fun createBtcTransaction(
        fromAddress: String,
        toAddress: String,
        amount: BigInteger,
        fee: BigInteger,
        privateKey: ByteArray,
        unspent: List<UnspentOutput>
    ): Transaction {
        val sorted = unspent.sortedBy { it.satoshis.toLong() }

        val transaction = Transaction(networkParameters)
        val to = Address.fromBase58(networkParameters, toAddress)

        transaction.addOutput(Coin.valueOf(amount.toLong()), to)

        val amountWithFee = amount.add(fee)

        val totalAmount = calculateTotalAmount(amountWithFee, sorted)
        if (totalAmount.toLong() < amountWithFee.toLong()) {
            throw IllegalStateException("You have insufficient funds for this transaction")
        }

        val change = totalAmount.subtract(amountWithFee)

        if (change.toLong() != 0L) {
            transaction.addOutput(
                Coin.valueOf(change.toLong()),
                Address.fromBase58(networkParameters, fromAddress)
            )
        }

        val signKey = DeterministicKey.fromPrivate(privateKey)
        computeChangeAmount(
            unspent,
            transaction,
            signKey,
            networkParameters,
            amountWithFee
        )

        //transaction.confidence.source = TransactionConfidence.Source.SELF
        transaction.purpose = Transaction.Purpose.USER_PAYMENT
        return transaction
    }

    private fun calculateTotalAmount(
        requiredAmount: BigInteger,
        unspent: List<UnspentOutput>
    ): BigInteger {
        var totalAmount = BigInteger.ZERO
        for (unspentOutput in unspent) {
            if (unspentOutput.script != null) {
                totalAmount = totalAmount.add(unspentOutput.satoshis)
                if (totalAmount.toLong() >= requiredAmount.toLong()) {
                    break
                }
            }
        }

        return totalAmount
    }

    private fun computeChangeAmount(
        unspentOutputs: List<UnspentOutput>,
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
        unspentOutput: UnspentOutput,
        currentNetParams: NetworkParameters,
        transaction: Transaction,
        key: ECKey,
        amountFromOutput: BigInteger?
    ): BigInteger? {
        if (unspentOutput.satoshis.toLong() != 0L) {
            val sha256Hash = Sha256Hash.wrap(Utils.parseAsHexOrBase58(unspentOutput.txid))

            if (unspentOutput.script != null) {
                val outPoint = TransactionOutPoint(
                    currentNetParams,
                    unspentOutput.outputIndex!!.toLong(),
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
                return amountFromOutput!!.add(unspentOutput.satoshis)
            }
        }

        return amountFromOutput
    }

    private fun generateFakeTransactionSource(): Pair<String, String> {
        val seed = TestValues.MNEMONIC
        val address = generatePublicAddress()

        val private = Hex.toHexString(generatePrivateKey(seed))

        return Pair(address, private)
    }

    private fun generatePublicAddress(): String {
        val private = ECKey.fromPrivate(generatePrivateKey(TestValues.MNEMONIC))
        return private.toAddress(networkParameters).toString()
    }

    private fun generatePrivateKey(mnemonic: String): ByteArray {
        val key = generate(mnemonic)
        return key.privKeyBytes
    }

    private fun generate(source: String): DeterministicKey {
        val seed: DeterministicSeed?
        seed = DeterministicSeed(
            source,
            null,
            "",
            DeterministicHierarchy.BIP32_STANDARDISATION_TIME_SECS.toLong()
        )
        val pathParent = arrayListOf<ChildNumber>().apply {
            add(ChildNumber(44, true))
            add(ChildNumber(networkParameters.childNumber(), true))
            add(ChildNumber(0, true))
            add(ChildNumber(0, false))
            add(ChildNumber(0, false))
        }

        val keyChainGroup = KeyChainGroup(networkParameters, seed)

        return keyChainGroup.activeKeyChain!!.getKeyByPath(pathParent, true)
    }

    private fun NetworkParameters.childNumber() =
        when (this) {
            is MainNetParams -> 0
            else -> 1
        }

    fun String.toSafeBigDecimal(): BigDecimal = try {
        toBigDecimal()
    } catch (exception: Exception) {
        BigDecimal.ZERO
    }

    data class UnspentOutput(
        var address: String? = null,
        var txid: String? = null,
        var outputIndex: BigInteger? = null,
        var script: String? = null,
        var satoshis: BigInteger,
        var height: BigInteger? = null,
        var stake: Boolean? = null
    )
}