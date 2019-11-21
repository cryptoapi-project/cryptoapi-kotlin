package io.pixelplex.model.response

import com.google.gson.annotations.SerializedName

data class EthContractBytecodeResponse (
    @SerializedName(BYTECODE_KEY)
    val bytecode: String
): CryptoApiResponse {
    companion object {
        const val BYTECODE_KEY = "bytecode"
    }
}