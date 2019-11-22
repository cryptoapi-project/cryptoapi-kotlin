# Crypto API android framework

The kotlin library is used for Crypto API in android development.

## Installation

This framework can be obtained through gradle dependency:

```
implementation 
```

## Usage

```kotlin
import io.pixelplex.cryptoapi_android_framework

private val cryptoApiFramework = CryptoApiFramework.getInstance(
    CALL_TIMEOUT,
    CONNECT_TIMEOUT,
    TOKEN
)

private val estimatedGas = EthEstimatedGasCallBody(
    from = ADDRESS_FROM,
    to = ADDRESS_TO,
    value = "10"
)

cryptoApiFramework.cryptoApiEth.estimateGas(
    estimatedGas,
    { estimatedGasResp -> /* when successful */ },
    { estimatedGasError -> /* when error */ }
)
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[PixelPlex](https://choosealicense.com/licenses/mit/)