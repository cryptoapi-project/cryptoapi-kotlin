package io.pixelplex.tools

import com.google.gson.Gson
import io.pixelplex.model.exception.ApiException
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class TypedCallback<T> private constructor(
    private val klass: Class<T>,
    private val onSuccess: (T) -> Unit,
    private val onError: (ApiException) -> Unit
) : Callback {

    override fun onFailure(call: Call, e: IOException) {
        onError(ApiException(e))
    }

    override fun onResponse(call: Call, response: Response) {
        val jsonString = response.body?.string()
        onSuccess(Gson().fromJson(jsonString, klass))
    }

    companion object {
        fun <T> withType(
            klass: Class<T>,
            onSuccess: (T) -> Unit,
            onError: (ApiException) -> Unit
        ): TypedCallback<T> {
            return TypedCallback(klass, onSuccess, onError)
        }
    }

}