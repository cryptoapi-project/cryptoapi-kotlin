package io.pixelplex.model.data

data class EthContractCallBody (
    val sender: String,
    val amount: Long,
    val bytecode: String
): EthCallBody