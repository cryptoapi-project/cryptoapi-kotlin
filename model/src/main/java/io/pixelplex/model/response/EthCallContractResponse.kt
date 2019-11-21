package io.pixelplex.model.response

/**
 * Implementation of [CryptoApiResponse]
 * Combines some specific properties of ETH contract call response
 *
 * @author Sergey Krupenich
 */
data class EthCallContractResponse (
    val response: String
): CryptoApiResponse