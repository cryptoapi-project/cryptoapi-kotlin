package io.pixelplex.mobile.cryptoapi.model.data.eth

/**
 * Implementation of [EthCallBody]
 * Combines some specific properties of body requests for ETH raw transaction calling
 *
 * @author Sergey Krupenich
 */
data class EthTransactionRawCall (
    val tx: String
): EthCallBody