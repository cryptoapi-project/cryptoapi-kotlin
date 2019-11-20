package io.pixelplex.cryptoapi_android_framework.core.model.data

data class TransactionExternal (
    val addresses: EthAddresses,
    val skip: Int,
    val limit: Int
)