# Using CryptoApiLib library with [Bitcoinj](https://bitcoinj.github.io)

## Get started
Сonfigure and return the object of the CryptoAPI class, which will allow to perform all the operations provided by the CryptoApiLib library.
Further, we can use the obtained method to get the CryptoAPI object anywhere in the program.
```kotlin
private val apiClient by lazy {
    CryptoApiFramework.getInstance(
        callTimeout = CALL_TIMEOUT,
        connectTimeout = CONNECT_TIMEOUT,
        readTimeOut = READ_TIMEOUT
    ).bitcoinAsyncApi
}
```

## Constant
There is a companion object that contains some constants.
```kotlin
companion object {
    private const val BTC_MNEMONIC = "YOUR_BTC_MNEMONIC"
    private const val FROM_ADDRESS = "SENDER_ADDRESS"
    private const val TO_ADDRESS = "RECIPIENT_ADDRESS"
    
    private const val CALL_TIMEOUT = 30000L
    private const val READ_TIMEOUT = 30000L
    private const val CONNECT_TIMEOUT = 15000L

    private const val MAINNET_DERIVATION_PART = 0
    private const val TESTNET_DERIVATION_PART = 1
    
    private const val AMOUNT = 1000
    private const val KB_VALUE = 1000

    private const val CRYPTO_API_BTC_LOG_KEY = "BTC_CRYPTO_API_LOG"
}
```

## Additional properties
There are some additional properties which presented below.
```kotlin
/**
 * Represents BTC network 
 */
private val networkParams by lazy {
    TestNet3Params.get()
}

/**
 * Key for address, private or public key generation
 */
private val deterministicKey by lazy {
    val seed = DeterministicSeed(
        BTC_MNEMONIC,
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

    val wallet = Wallet.fromSeed(networkParams, seed, Script.ScriptType.P2PKH)

    wallet.activeKeyChain!!.getKeyByPath(pathParent, true)
}
```

### Generate address. Get outputs.
The following is an example that shows how to `generate address and obtain unspent outputs` for it using CryptoApiLib.
```kotlin
val ecKey = ECKey.fromPrivate(deterministicKey.privKeyBytes)
val address = Address.fromKey(networkParams, ecKey, Script.ScriptType.P2PKH).toString()

// MARK: Get outputs
// get unspent outputs to calculate balance or build the transaction
val outputs = try {
    apiClient.getOutputs(BtcOutputStatus.unspent, listOf(address))
} catch (ex: Exception) {
    ex.printStackTrace()
    emptyList<BtcOutput>()
}
printLog(outputs.toString())
```

### Create Transaction
CryptoAPI allows you to send raw transactions, but before that you need to prepare it. As a result, you will get a transaction ready to be sent to the network.
`Creating a transaction` is as follows:
```kotlin
// prepare values for transaction
val fromAddress = FROM_ADDRESS
val toAddress = TO_ADDRESS
val amountWithWei = AMOUNT.toBigInteger()
val fee = 1000.toBigInteger()
val privateKey = Hex.toHexString(deterministicKey.privKeyBytes)

//create signed transaction
val signedTransaction = createSignedTransaction(
    fromAddress, toAddress, amountWithWei, fee, Hex.decode(privateKey), outputs
)
```

### Send Transaction
It remains only to `send` the transaction by sending a hash of the transaction using CryptoApi.
```kotlin
// get a transaction hex and send it with CryptoApi
val transactionHex = Hex.toHexString(signedTransaction.unsafeBitcoinSerialize())
try {
    apiClient.sendRawTransaction(BtcRawTransaction(transactionHex))
    printLog("Send success")
} catch (ex: Exception) {
    printLog("Send failure")
}
```

### Calculate fee for transaction
You can `calculate fee` for your transaction based on fee rate for kilobyte.
```kotlin
// first of all, you need to get fee rate for kilobyte
// response has result like "0.00001". Convert it to satoshi if necessary.
val estimatedFeeResponse = apiClient.estimateFee()
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
printLog("estimated fee = $estimatedFee")
```

## License

The MIT License (MIT)

Copyright (c) 2019 PixelPlex Inc. <https://pixelplex.io>

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
'Software'), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
