package io.pixelplex.mobile.cryptoapi.model.data.btc

enum class BtcOutputStatus(val string: String) {
    spent("spent"),
    unspent("unspent")
}