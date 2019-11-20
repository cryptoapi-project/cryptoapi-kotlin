package io.pixelplex.model.data

data class EthTransfer (
    val addresses: EthAddresses,
    val skip: Int,
    val limit: Int,
    val positive: String = ""
)