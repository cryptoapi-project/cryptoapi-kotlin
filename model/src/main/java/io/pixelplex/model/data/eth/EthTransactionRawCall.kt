package io.pixelplex.model.data.eth

import io.pixelplex.model.data.eth.EthCallBody

/**
 * Implementation of [EthCallBody]
 * Combines some specific properties of body requests for ETH raw transaction calling
 *
 * @author Sergey Krupenich
 */
data class EthTransactionRawCall (
    val tx: String
): EthCallBody