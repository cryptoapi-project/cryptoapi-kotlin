package io.pixelplex.cryptoapi.example.util

object Constants {
    const val HEX_PREFIX = "0x"

    // CryptoApi apikey.io
    const val CRYPTO_API_TOKEN = "<YOUR_TOKEN>"

    const val ETH_CHAIN_ID = 4 // testnet ethereum network (Rinkeby)

    const val ETH_FROM_ADDRESS = "<ETH_ADDRESS>"
    const val ETH_TO_ADDRESS = "<ETH_ADDRESS>"
    const val ETH_CONTRACT_ADDRESS = "<ETH_ADDRESS>"

    const val REQUEST_CALL_TIMEOUT = 30000L
    const val REQUEST_READ_TIMEOUT = 30000L
    const val REQUEST_CONNECT_TIMEOUT = 15000L
    const val SEND_AMOUNT_WEI = 100000000000000L
    const val LOG_TAG = "ETH"
}