package io.pixelplex.model.data

data class EthTransaction (
    val from: String,
    val to: String,
    val skip: Int,
    val limit: Int
)