package io.pixelplex.model.data.eth

import io.pixelplex.model.data.eth.SkipLimitEthCallBody

/**
 * Implementation of [SkipLimitEthCallBody]
 * Combines some specific properties of body requests for ETH token balance getting
 *
 * @author Sergey Krupenich
 */
data class EthTokenBalanceCall (
    override val skip: Int,
    override val limit: Int,
    val address: String
): SkipLimitEthCallBody()