package io.pixelplex.cryptoapi_android_framework.core.model.data

data class EstimatedGasBody (
    val from: String,
    val to: String,
    val data: String? = null,
    val value: String
)