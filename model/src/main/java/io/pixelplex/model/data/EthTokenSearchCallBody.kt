package io.pixelplex.model.data

/**
 * Implementation of [SkipLimitEthCallBody]
 * Combines some specific properties of body requests for ETH token searching
 *
 * @author Sergey Krupenich
 */
data class EthTokenSearchCallBody (
    val query: String,
    override val skip: Int,
    override val limit: Int,
    val types: EthTypedParams
): SkipLimitEthCallBody()