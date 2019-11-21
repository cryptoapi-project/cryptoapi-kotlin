package io.pixelplex.model.data

data class EthTransactionCallBody (
    val from: String,
    val to: String,
    override val skip: Int,
    override val limit: Int
): SkipLimitEthCallBody()