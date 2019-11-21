package io.pixelplex.model.data

data class EthTokenSearchCallBody (
    val query: String,
    override val skip: Int,
    override val limit: Int,
    val types: EthTypedParams
): SkipLimitEthCallBody()