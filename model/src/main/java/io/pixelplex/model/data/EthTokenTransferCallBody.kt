package io.pixelplex.model.data

data class EthTokenTransferCallBody (
    override val skip: Int,
    override val limit: Int,
    val typedParams: EthTypedParams,
    val token: String
): SkipLimitEthCallBody()