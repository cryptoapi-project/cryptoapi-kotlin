package io.pixelplex.model.data.eth

import io.pixelplex.model.data.eth.EthCallBody

/**
 * Implementation of [EthCallBody]
 * Combines some specific properties of body requests for ETH estimated gas getting
 *
 * @author Sergey Krupenich
 */
data class EthEstimatedGasCall (
    val from: String,
    val to: String,
    val data: String? = null,
    val value: String
): EthCallBody