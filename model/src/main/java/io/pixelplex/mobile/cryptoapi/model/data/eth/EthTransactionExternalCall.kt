package io.pixelplex.mobile.cryptoapi.model.data.eth

/**
 * Implementation of [SkipLimitEthCallBody]
 * Combines some specific properties of body requests for ETH external transaction getting
 *
 * @author Sergey Krupenich
 */
data class EthTransactionExternalCall (
    val typedParams: EthTypedParams,
    override val skip: Int,
    override val limit: Int
): SkipLimitEthCallBody()