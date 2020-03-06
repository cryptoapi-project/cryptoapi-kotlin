package io.pixelplex.mobile.cryptoapi.model.data.eth

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