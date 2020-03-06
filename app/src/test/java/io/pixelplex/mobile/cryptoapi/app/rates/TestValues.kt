package io.pixelplex.mobile.cryptoapi.app.rates

import io.pixelplex.mobile.cryptoapi.model.data.rates.RateTypedParams

object TestValues {
    const val ETH_COIN = "ETH"
    const val BTC_COIN = "BTC"
    const val BCH_COIN = "BCH"

    val rateAddresses = RateTypedParams(
        ETH_COIN,
        BTC_COIN,
        BCH_COIN
    )
}