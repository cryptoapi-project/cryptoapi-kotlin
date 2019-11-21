package io.pixelplex.model.data

data class EthTransfer (
    val typedParams: EthTypedParams,
    val skip: Int,
    val limit: Int,
    val positive: String = ""
)