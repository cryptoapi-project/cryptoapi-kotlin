package io.pixelplex.model.data.btc

enum class BtcOutputStatus(val string: String) {
    spent("spent"),
    unspent("unspent")
}