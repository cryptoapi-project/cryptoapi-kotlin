package io.pixelplex.model.data

data class TokensTransfersCallBody (
    val skip: Int,
    val limit: Int,
    val typedParams: EthTypedParams,
    val token: String
)