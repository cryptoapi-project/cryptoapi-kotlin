package io.pixelplex.model.data

data class TokensTransfersCallBody (
    val skip: Int,
    val limit: Int,
    val addresses: EthAddresses,
    val token: String
)