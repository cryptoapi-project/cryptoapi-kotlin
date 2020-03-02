# Using CryptoApiLib library

## Get started
Сonfigure and return the object of the CryptoAPI class, which will allow to perform all the operations provided by the CryptoApiLib library.
Further, we can use the obtained method to get the CryptoAPI object anywhere in the program.
```kotlin
private val apiClient by lazy {
    CryptoApiFramework.getInstance(
        CALL_TIMEOUT,
        CONNECT_TIMEOUT,
        READ_TIMEOUT,
        CRYPTO_API_KEY,
        CryptoApi.URL.TESTNET
    ).ethereumAsyncApi
}
```

## Constant
There is a companion object that contains some constants.
```kotlin
companion object {
    private const val CRYPTO_API_ETH_EXAMPLE_KEY = "ETH_LOG"

    private const val CALL_TIMEOUT = 30000L
    private const val READ_TIMEOUT = 30000L
    private const val CONNECT_TIMEOUT = 15000L

    private const val CRYPTO_API_KEY = "YOUR_TOKEN"

    private const val ETH_ADDRESS_1 = "ADDRESS_1"
    private const val ETH_ADDRESS_2 = "ADDRESS_2"

    private const val TX =
        "0xf86e8386ca038602bba7f5220083632ea0941de29f644d555fe9cc3241e1083de0868f959bfa8545d964b800801ca04ef1f13c58af9a9ac4be66b838a238b24db798d585d882865637fdc35bdc49c4a04b7d1dfc3d9672080347a0d3559628f5f757bd6f6a005d1c4f7cdccce020ea02"
}
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
}
```

## Create and send a transaction
CryptoAPI allows you to send raw transactions, but before that you need to prepare it.
Creating and sending a transaction is as follows:
```kotlin
private val ethTransactionRawBody =
    EthTransactionRawCall(
        tx = TX
    )

try {
    val result = apiClient.sendRawTransaction(ethTransactionRawBody)
    logD(result)
} catch (e: Exception) {
    val errors = (e as ApiException).errors
    errors.forEach {
        logD(it.message)
    }
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