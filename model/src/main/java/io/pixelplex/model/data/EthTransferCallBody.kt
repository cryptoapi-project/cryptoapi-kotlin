package io.pixelplex.model.data

/**
 * Implementation of [SkipLimitEthCallBody]
 * Combines some specific properties of body requests for ETH transfer calling
 *
 * @author Sergey Krupenich
 */
data class EthTransferCallBody (
    val typedParams: EthTypedParams,
    override val skip: Int,
    override val limit: Int,
    val positive: String = ""
): SkipLimitEthCallBody()