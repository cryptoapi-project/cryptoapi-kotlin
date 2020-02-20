package io.pixelplex.model.common

import com.google.gson.annotations.SerializedName

/**
 * Contains all supported coins of Crypto API
 *
 * @author Sergey Krupenich
 */
enum class Coin(signature: String) {
    @SerializedName("eth")
    ETH("eth"),

    @SerializedName("btc")
    BTC("btc"),

    @SerializedName("bch")
    BCH("bch")
}