package io.pixelplex.cryptoapi_android_framework.core.model.data

data class EthTransaction (
    val from: String,
    val to: String,
    val skip: Int,
    val limit: Int
)