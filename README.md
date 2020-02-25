# CryptoAPI-android-framework

CryptoAPI is Kotlin API Wrapper framework. Designed to receive information about transactions, balances and send transactions.

## Install

This framework can be obtained through Maven (https://dl.bintray.com/pixelplex/mobile):

```
<dependency>
	<groupId>io.pixelplex.cryptoapiframework</groupId>
	<artifactId>cryptoapiframework</artifactId>
	<version>0.3.2</version>
	<type>pom</type>
</dependency>
```

or Gradle:
```
implementation 'io.pixelplex.cryptoapiframework:cryptoapiframework:0.3.2'
```
## Setup

add file cryptoapi.properties into /app project folder.

cryptoapi.properties:
```java
CRYPTO_API_KEY="5de552d7efc6ff2e1b09d946cc5263e346003a93ab28bf2ffeb24979da85a1f5"
```
For setup framework use this simple code:
```java
private val apiClient = CryptoApiFramework.getInstance(
        CALL_TIMEOUT,
        CONNECT_TIMEOUT,
        READ_TIMEOUT,
        BuildConfig.CRYPTO_API_KEY
    )
```

## Usage

### Networks

CryptoAPI supports `MAINNET` and `TESTNET` chains. You can select chain type by `URL` field when setup framework (Mainnet by default)
```java
private val apiClient = CryptoApiFramework.getInstance(
        CALL_TIMEOUT,
        CONNECT_TIMEOUT,
        READ_TIMEOUT,
        BuildConfig.CRYPTO_API_KEY,
        CryptoApi.URL.TESTNET
    )
```

### Servicies

CryptoAPI contains 4 main api's for usage.
```java
BTC (sync/async)
BCH (sync/async)
ETH (sync/async)
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