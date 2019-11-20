package io.pixelplex.cryptoapi_android_framework.core.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.pixelplex.cryptoapi_android_framework.core.model.response.CryptoApiResponse
import io.pixelplex.cryptoapi_android_framework.core.model.response.ErrorResponse

data class EthContractBytecodeResponse (
    @SerializedName(BYTECODE_KEY) @Expose
    val bytecode: String,

    @SerializedName(ERRORS_KEY)
    val errors: List<ErrorResponse>? = null,

    @SerializedName(STATUS_KEY)
    val status: Int? = null
): CryptoApiResponse {
    companion object {
        const val BYTECODE_KEY = "bytecode"
        const val ERRORS_KEY = "errors"
        const val STATUS_KEY = "status"
    }
}