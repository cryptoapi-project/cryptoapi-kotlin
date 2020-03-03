# Using CryptoApiLib library with with [Bitcoinj](https://bitcoinj.github.io)

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
    ).bitcoinAsyncApi
}
```

## Constant
There is a companion object that contains some constants.
```kotlin
companion object {
    private const val CRYPTO_API_KEY = "YOUR_CRYPTO_API_KEY"
    
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