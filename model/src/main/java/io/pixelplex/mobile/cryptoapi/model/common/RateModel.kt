package io.pixelplex.mobile.cryptoapi.model.common

import java.util.*

/**
 * Contains info about rate of a coin
 *
 * @author Sergey Krupenich
 */
data class RateModel(
    var symbol: String,
    var rate: Map<String, String>,
    var last_updated: String
)

/**
 * Contains info about history rate of a coin
 *
 * @author Sergey Krupenich
 */
data class HistoryRateModel(
    var symbol: String,
    var rates: List<Rate>
)

/**
 * Contains info about history rate item of a coin
 *
 * @author Sergey Krupenich
 */
data class Rate(
    var created_at: Date,
    var rate: Map<String, String>
)