package io.pixelplex.mobile.cryptoapi.model.data.eth

/**
 * Implementation of [SkipLimitEthCallBody]
 * Combines some specific properties of body requests for ETH transfer calling
 *
 * @author Sergey Krupenich
 */
data class EthTransferCall (
    val typedParams: EthTypedParams,
    override val skip: Int,
    override val limit: Int,
    val positive: String = ""
): SkipLimitEthCallBody()