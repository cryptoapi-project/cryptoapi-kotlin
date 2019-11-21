package io.pixelplex.model.data

data class EthEstimatedGasCallBody (
    val from: String,
    val to: String,
    val data: String? = null,
    val value: String
): EthCallBody