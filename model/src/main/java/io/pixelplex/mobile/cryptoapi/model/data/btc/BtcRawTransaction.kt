package io.pixelplex.mobile.cryptoapi.model.data.btc

import com.google.gson.annotations.SerializedName

data class BtcRawTransaction(

    @SerializedName("hash")
    val hash: String
)