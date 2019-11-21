package io.pixelplex.model.data

data class EthTransactionExternalCallBody (
    val typedParams: EthTypedParams,
    override val skip: Int,
    override val limit: Int
): SkipLimitEthCallBody()