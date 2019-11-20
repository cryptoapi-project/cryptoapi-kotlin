package io.pixelplex.cryptoapi_android_framework.core.model.data

data class EthContractCallBody (
    val sender: String,
    val amount: Long,
    val bytecode: String
)