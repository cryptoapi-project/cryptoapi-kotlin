package io.pixelplex.model.data

/**
 * Implementation of [SkipLimitEthCallBody]
 * Combines some specific properties of body requests for ETH external transaction getting
 *
 * @author Sergey Krupenich
 */
data class EthTransactionExternalCallBody (
    val typedParams: EthTypedParams,
    override val skip: Int,
    override val limit: Int
): SkipLimitEthCallBody()