package io.pixelplex.model.data.btc

import com.google.gson.annotations.SerializedName
import io.pixelplex.model.data.btc.BtcBlock
import java.math.BigInteger

data class BtcBlocks(

    @SerializedName("skip")
    val skippedCount: BigInteger,

    @SerializedName("limit")
    val limitCount: BigInteger,

    @SerializedName("count")
    val count: BigInteger,

    @SerializedName("items")
    val items: List<BtcBlock>
)