package io.pixelplex.mobile.cryptoapi.model.data.btc

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class BtcTransactions(

    @SerializedName("block_height_or_hash")
    val blockHeightOrHash: BigInteger,

    @SerializedName("skip")
    val skippedCount: BigInteger,

    @SerializedName("limit")
    val limitCount: BigInteger,

    @SerializedName("from")
    val from: String?,

    @SerializedName("to")
    val to: String?,

    @SerializedName("items")
    val items: List<BtcTransaction>
)