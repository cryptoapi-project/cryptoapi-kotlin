package io.pixelplex.mobile.cryptoapi.model.data.push

import com.google.gson.annotations.SerializedName

data class NotificationResponse (
    @SerializedName("addresses")
    val addresses: List<String>,

    @SerializedName("token")
    val token: String
)