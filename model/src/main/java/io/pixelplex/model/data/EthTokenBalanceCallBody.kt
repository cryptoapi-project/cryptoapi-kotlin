package io.pixelplex.model.data

/**
 * Implementation of [SkipLimitEthCallBody]
 * Combines some specific properties of body requests for ETH token balance getting
 *
 * @author Sergey Krupenich
 */
data class EthTokenBalanceCallBody (
    override val skip: Int,
    override val limit: Int,
    val address: String
): SkipLimitEthCallBody()