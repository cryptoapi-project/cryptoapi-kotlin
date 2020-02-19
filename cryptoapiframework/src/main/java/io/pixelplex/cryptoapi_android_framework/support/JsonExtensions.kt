package io.pixelplex.cryptoapi_android_framework.support

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> fromJson(json: String): T {
    val token = object : TypeToken<T>() {}.type
    return if (token == object : TypeToken<String>() {}.type) {
        json as T
    } else {
        Gson().fromJson(json, token)
    }
}