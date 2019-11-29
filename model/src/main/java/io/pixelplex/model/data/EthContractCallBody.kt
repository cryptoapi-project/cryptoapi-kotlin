package io.pixelplex.model.data

/**
 * Implementation of [EthCallBody]
 * Combines some specific properties of body requests for ETH contract calling
 *
 * @author Sergey Krupenich
 */
data class EthContractCallBody (
    val sender: String,
    val amount: String,
    val bytecode: String
): EthCallBody