# Using CryptoApiLib library with [Bitcoinj](https://bitcoinj.github.io)

## Get started
Сonfigure and return the object of the CryptoAPI class, which will allow to perform all the operations provided by the CryptoApiLib library.
Further, we can use the obtained method to get the CryptoAPI object anywhere in the program.
```kotlin
private val apiClient by lazy {
    val configuration = CryptoApiConfiguration(
            callTimeout = CALL_TIMEOUT,
            connectTimeout = CONNECT_TIMEOUT,
            readTimeOut = READ_TIMEOUT
        )

    private val apiClient =
        CryptoApiFramework.getInstance(configuration).ethereumAsyncApi
}
```

## Constant
There is a companion object that contains some constants.
```kotlin
companion object {
    private const val PREFIX = "0x"

    private const val ETH_ADDRESS_1 = "SENDER_ADDRESS"
    private const val ETH_ADDRESS_2 = "RECIPIENT_ADDRESS"

    private const val CALL_TIMEOUT = 30000L
    private const val READ_TIMEOUT = 30000L
    private const val CONNECT_TIMEOUT = 15000L
    private const val SEND_AMOUNT = 10000000L

    private const val WEB3J_ETH_EXAMPLE_KEY = "ETH_LOG"
}
```

## Additional properties
There are some additional properties which presented below.
```kotlin
/**
 * Send amount Big integer object initializing
 */
private val sendAmountBigInt =
    BigInteger.valueOf(SEND_AMOUNT)

/**
 * Your private key byte array
 */
private val privateKey = ByteArray(0)
```

## Getting a balance
The following code demonstrates how to obtain a balance using CryptoApiLib.
```kotlin
private val ethAddresses = EthTypedParams(
    ETH_ADDRESS_1,
    ETH_ADDRESS_2
)

val balances = apiClient.getBalances(ethAddresses.getList())
balances.forEach {
    logD(it.balance)
}
```

## Estimating a gas
Now, before creating a transaction, you need to get a `gas estimate`.
```kotlin
private val estimatedGas = EthEstimatedGasCall(
    from = ETH_ADDRESS_1,
    to = ETH_ADDRESS_2,
    value = "10"
)

GlobalScope.launch {
    val estimationGas = apiClient.estimateGas(estimatedGas)
    logD(estimationGas.estimateGas.toString())
    ...
}
```

## Create and send a transaction
CryptoAPI allows you to send raw transactions, but before that you need to prepare it.
Creating and sending a transaction is as follows:
```kotlin
GlobalScope.launch {
    
    ...
    val addressWithoutPrefix = ETH_ADDRESS_2.removePrefix(PREFIX)
    val addressBytes = Hex.decode(addressWithoutPrefix)

    val transaction =
        EthTransaction(
            BigInteger.valueOf(estimationGas.nonce.toLong()).toBytes(),
            BigInteger.valueOf(estimationGas.gasPrice).toBytes(),
            BigInteger.valueOf(estimationGas.estimateGas).toBytes(),
            addressBytes,
            sendAmountBigInt.toBytes(),
            null
        )

    val senderKey = EthECKey.fromPrivate(privateKey)
    transaction.sign(senderKey)
    val tx = getTx(Hex.toHexString(transaction.encoded))

    val result = apiClient.sendRawTransaction(EthTransactionRawCall(tx))
    logD(result)
}
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