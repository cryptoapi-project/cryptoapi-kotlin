package io.pixelplex.model.data

data class TransactionExternal (
    val typedParams: EthTypedParams,
    val skip: Int,
    val limit: Int
)