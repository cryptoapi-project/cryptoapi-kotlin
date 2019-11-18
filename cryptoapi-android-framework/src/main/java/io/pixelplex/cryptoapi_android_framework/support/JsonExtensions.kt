package io.pixelplex.cryptoapi_android_framework.support

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun <T> fromJson(json: String?): T {
    return Gson().fromJson(json, object : TypeToken<T>() {}.type)
}