package io.pixelplex.model.data

data class EthTokensBalancesBody (
    val skip: Int,
    val limit: Int,
    val address: String
)