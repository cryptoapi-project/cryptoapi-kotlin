package io.pixelplex.tools

import com.google.gson.Gson

inline fun <reified T> T.json(): String = Gson().toJson(this, T::class.java)
inline fun <reified T> String.fromJson(c: Class<T>): T = Gson().fromJson(this, c)