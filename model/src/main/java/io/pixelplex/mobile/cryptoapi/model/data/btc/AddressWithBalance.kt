package io.pixelplex.mobile.cryptoapi.model.data.btc

import com.google.gson.annotations.SerializedName

data class AddressWithBalance(

    @SerializedName("address")
    val address: String,

    @SerializedName("balance")
    val balance: Balance
) {

    data class Balance(
        @SerializedName("spent")
        val spent: String,

        @SerializedName("unspent")
        val unspent: String,

        @SerializedName("confirmed")
        val confirmed: String,

        @SerializedName("unconfirmed")
        val unconfirmed: String
    )
}