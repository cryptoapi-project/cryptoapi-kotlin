package io.pixelplex.mobile.cryptoapi.model.data.btc

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class BtcTransactions(

    @SerializedName("skip")
    val skippedCount: BigInteger,

    @SerializedName("limit")
    val limitCount: BigInteger,

    @SerializedName("count")
    val count: BigInteger,

    @SerializedName("items")
    val items: List<BtcTransaction>
)