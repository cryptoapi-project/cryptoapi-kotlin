package io.pixelplex.cryptoapi.example.util

import java.math.BigInteger

fun BigInteger?.toBytes(): ByteArray? {
    if (this == null)
        return null

    var data = this.toByteArray()

    if (data.size != 1 && data[0].toInt() == 0) {
        val tmp = ByteArray(data.size - 1)
        System.arraycopy(data, 1, tmp, 0, tmp.size)
        data = tmp
    }
    return data
}

fun String.withPrefix(prefix: String) = "$prefix$this"