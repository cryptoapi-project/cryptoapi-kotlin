package io.pixelplex.cryptoapi.example.util

import com.google.protobuf.ByteString
import java.math.BigInteger
import kotlin.experimental.and

fun String.withPrefix(prefix: String) = "$prefix$this"

fun BigInteger.toByteString(): ByteString {
    return ByteString.copyFrom(this.toByteArray())
}

fun ByteArray.toHexString(withPrefix: Boolean = true): String {
    val stringBuilder = StringBuilder()
    if (withPrefix) {
        stringBuilder.append("0x")
    }
    for (element in this) {
        stringBuilder.append(String.format("%02x", element and 0xFF.toByte()))
    }
    return stringBuilder.toString()
}

fun String.hexStringToByteArray(): ByteArray {
    val HEX_CHARS = "0123456789ABCDEF"
    val result = ByteArray(length / 2)
    for (i in 0 until length step 2) {
        val firstIndex = HEX_CHARS.indexOf(this[i].toUpperCase())
        val secondIndex = HEX_CHARS.indexOf(this[i + 1].toUpperCase())
        val octet = firstIndex.shl(4).or(secondIndex)
        result.set(i.shr(1), octet.toByte())
    }
    return result
}
