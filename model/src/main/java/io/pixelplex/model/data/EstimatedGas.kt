package io.pixelplex.model.data

data class EstimatedGas (
    val from: String,
    val to: String,
    val data: String? = null,
    val value: String
)