package io.pixelplex.model.data.btc

import com.google.gson.annotations.SerializedName

data class BtcRawTransaction(

    @SerializedName("hash")
    val hash: String
)