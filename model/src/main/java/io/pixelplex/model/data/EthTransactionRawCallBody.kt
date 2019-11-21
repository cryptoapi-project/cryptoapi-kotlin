package io.pixelplex.model.data

/**
 * Implementation of [EthCallBody]
 * Combines some specific properties of body requests for ETH raw transaction calling
 *
 * @author Sergey Krupenich
 */
data class EthTransactionRawCallBody (
    val tx: String
): EthCallBody