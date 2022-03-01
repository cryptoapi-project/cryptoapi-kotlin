package io.pixelplex.cryptoapi.example.util

import java.math.BigDecimal
import java.math.BigInteger

fun String.withPrefix(prefix: String) = "$prefix$this"

fun btcToSatoshi(btcValue: BigDecimal): BigDecimal {
    return btcValue.multiply(BigDecimal(Constants.ONE_BTC_IN_SATOSHI))
        .setScale(0, BigDecimal.ROUND_HALF_DOWN)
}