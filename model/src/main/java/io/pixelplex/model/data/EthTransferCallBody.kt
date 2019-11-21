package io.pixelplex.model.data

data class EthTransferCallBody (
    val typedParams: EthTypedParams,
    override val skip: Int,
    override val limit: Int,
    val positive: String = ""
): SkipLimitEthCallBody()