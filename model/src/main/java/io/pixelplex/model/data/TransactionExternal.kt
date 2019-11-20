package io.pixelplex.model.data

import io.pixelplex.model.data.EthAddresses

data class TransactionExternal (
    val addresses: EthAddresses,
    val skip: Int,
    val limit: Int
)