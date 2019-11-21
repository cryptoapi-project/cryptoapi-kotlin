package io.pixelplex.model.data

data class EthTokensSearchBody (
    val query: String,
    val skip: Int,
    val limit: Int,
    val types: EthTypedParams
)