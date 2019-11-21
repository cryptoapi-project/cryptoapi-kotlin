package io.pixelplex.model.data

/**
 * Implementation of [SkipLimitEthCallBody]
 * Combines some specific properties of body requests for ETH transaction calling
 *
 * @author Sergey Krupenich
 */
data class EthTransactionCallBody (
    val from: String,
    val to: String,
    override val skip: Int,
    override val limit: Int
): SkipLimitEthCallBody()