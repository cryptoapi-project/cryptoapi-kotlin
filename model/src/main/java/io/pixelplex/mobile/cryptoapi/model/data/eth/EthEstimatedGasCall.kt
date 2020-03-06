package io.pixelplex.mobile.cryptoapi.model.data.eth

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