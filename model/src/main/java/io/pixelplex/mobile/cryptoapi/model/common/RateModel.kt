package io.pixelplex.mobile.cryptoapi.model.common

data class RateModel(
    var symbol: String,
    var rate: Map<String, String>,
    var last_updated: String
)