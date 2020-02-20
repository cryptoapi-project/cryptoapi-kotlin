package io.pixelplex.model.data.eth

import io.pixelplex.model.data.eth.EthCallBody

/**
 * Implementation of [EthCallBody]
 * Combines some specific properties of body requests for ETH contract calling
 *
 * @author Sergey Krupenich
 */
data class EthContractCall (
    val sender: String,
    val amount: Int,
    val bytecode: String
): EthCallBody