package io.pixelplex.mobile.cryptoapi.model.common

import com.google.gson.annotations.SerializedName

data class ApiResult<T>(
    @SerializedName("result")
    val result: T
)