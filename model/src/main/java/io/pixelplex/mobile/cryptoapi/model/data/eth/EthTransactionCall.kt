package io.pixelplex.mobile.cryptoapi.model.data.eth

/**
 * Implementation of [SkipLimitEthCallBody]
 * Combines some specific properties of body requests for ETH transaction calling
 *
 * @author Sergey Krupenich
 */
data class EthTransactionCall (
    val from: String,
    val to: String,
    override val skip: Int,
    override val limit: Int
): SkipLimitEthCallBody()