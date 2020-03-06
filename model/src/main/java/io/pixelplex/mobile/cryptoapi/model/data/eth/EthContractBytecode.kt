package io.pixelplex.mobile.cryptoapi.model.data.eth

import com.google.gson.annotations.SerializedName
import io.pixelplex.mobile.cryptoapi.model.common.CryptoApiResponse

/**
 * Implementation of [CryptoApiResponse]
 * Combines some specific properties of ETH contract bytecode response
 *
 * @author Sergey Krupenich
 */
data class EthContractBytecodeResponse (
    @SerializedName("bytecode")
    val bytecode: String
): CryptoApiResponse