[ ![Download](https://api.bintray.com/packages/pixelplex/mobile/cryptoapi-stable/images/download.svg) ](https://bintray.com/pixelplex/mobile/cryptoapi-stable/_latestVersion)
# CryptoApiLibrary

CryptoApiLibrary is Kotlin API Wrapper framework. Designed to receive information about transactions, balances and send transactions.

## Install

```
<dependency>
	<groupId>io.pixelplex.mobile.cryptoapi</groupId>
	<artifactId>cryptoapi</artifactId>
	<version>0.4.0</version>
	<type>pom</type>
</dependency>
```

or Gradle:
```
implementation 'io.pixelplex.mobile.cryptoapi:cryptoapi:0.4.0'
```
## Setup

For setup framework use simple code as below. All parameters are optional.
```kotlin
val configuration = CryptoApiConfiguration(
    authorizationToken = "<YOUR-API-TOKEN>",
    callTimeout = CALL_TIMEOUT,
    connectTimeout = CONNECT_TIMEOUT,
    readTimeOut = READ_TIMEOUT
)

private val apiClient =
    CryptoApiFramework.getInstance(configuration)
```

## Usage

### Networks

CryptoAPI supports `MAINNET` and `TESTNET` chains. You can select chain type by `URL` field when setup framework (Mainnet by default).
```kotlin
val configuration = CryptoApiConfiguration(
    authorizationToken = "<YOUR-API-TOKEN>",
    url = CryptoApi.URL.TESTNET
    callTimeout = CALL_TIMEOUT,
    connectTimeout = CONNECT_TIMEOUT,
    readTimeOut = READ_TIMEOUT
)

private val apiClient =
    CryptoApiFramework.getInstance(configuration)
```

### Servicies

CryptoAPI contains 3 main api's for usage.
```kotlin
BTC (sync/async)
BCH (sync/async)
ETH (sync/async)
```

## License

The MIT License (MIT)

Copyright (c) 2021 PixelPlex Inc. <https://pixelplex.io>

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