package io.pixelplex.mobile.cryptoapi.model.data.push

import com.google.gson.annotations.SerializedName

data class FirebaseToken (

    @SerializedName("firebase_token")
    val token: String
)