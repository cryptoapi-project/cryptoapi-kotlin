package io.pixelplex.model.data.eth

/**
 * Implementation of [SkipLimitEthCallBody]
 * Combines some specific properties of body requests for ETH token transfer info getting
 *
 * @author Sergey Krupenich
 */
data class EthTokenTransferCall (
    override val skip: Int,
    override val limit: Int,
    val typedParams: EthTypedParams,
    val token: String
): SkipLimitEthCallBody()