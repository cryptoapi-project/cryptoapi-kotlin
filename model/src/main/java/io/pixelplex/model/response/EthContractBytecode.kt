package io.pixelplex.model.response

import com.google.gson.annotations.SerializedName

/**
 * Implementation of [CryptoApiResponse]
 * Combines some specific properties of ETH contract bytecode response
 *
 * @author Sergey Krupenich
 */
data class EthContractBytecodeResponse (
    @SerializedName(BYTECODE_KEY)
    val bytecode: String
): CryptoApiResponse {
    companion object {
        const val BYTECODE_KEY = "bytecode"
    }
}