package io.pixelplex.tools

import io.pixelplex.model.exception.ApiException
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class TypedCallback<T>(
    private val onSuccess: (T) -> Unit,
    private val onError: (ApiException) -> Unit
) : Callback {

    override fun onFailure(call: Call, e: IOException) {
        onError(ApiException(e))
    }

    override fun onResponse(call: Call, response: Response) {
        onSuccess(fromJson(response.body?.string()))
    }

}