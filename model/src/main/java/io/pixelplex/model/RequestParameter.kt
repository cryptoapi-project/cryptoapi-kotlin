package io.pixelplex.model

import com.google.gson.Gson

class RequestParameter<T>(val value: T) {

    override fun toString(): String {
        return value.toString()
    }

    fun toGson(): String {
        return gson.toJson(value)
    }

    companion object {
        private val gson = Gson()
    }
}
