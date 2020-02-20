package io.pixelplex.model.data.eth

/**
 * Implementation of [SkipLimitEthCallBody]
 * Combines some specific properties of body requests for ETH token searching
 *
 * @author Sergey Krupenich
 */
data class EthTokenSearchCall (
    val query: String,
    override val skip: Int,
    override val limit: Int,
    val types: EthTypedParams
): SkipLimitEthCallBody()