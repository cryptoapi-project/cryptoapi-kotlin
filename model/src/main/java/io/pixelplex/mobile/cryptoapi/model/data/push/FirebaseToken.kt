package io.pixelplex.mobile.cryptoapi.model.data.push

import com.google.gson.annotations.SerializedName
import java.util.*

data class FirebaseToken (
    @SerializedName("firebase_token")
    val token: String,

    @SerializedName("types")
    val typesParam: String? = null
)

enum class NotificationType {
    @SerializedName("outgoing")
    OUTGOING,

    @SerializedName("incoming")
    INCOMING,

    @SerializedName("balance")
    BALANCE,

    @SerializedName("all")
    ALL;

    override fun toString(): String =
        super.toString().toLowerCase(Locale.ROOT)
}

public fun convertNotificationTypes(vararg notificationTypes: NotificationType) =
    notificationTypes.asList().toString()
        .replace("\\[|\\]|\\s+".toRegex(), "")
        .toLowerCase(Locale.ROOT)