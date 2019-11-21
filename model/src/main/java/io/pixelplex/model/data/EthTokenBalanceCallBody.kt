package io.pixelplex.model.data

data class EthTokenBalanceCallBody (
    override val skip: Int,
    override val limit: Int,
    val address: String
): SkipLimitEthCallBody()