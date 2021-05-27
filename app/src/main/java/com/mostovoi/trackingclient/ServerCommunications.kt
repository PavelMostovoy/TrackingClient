package com.mostovoi.trackingclient

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

object ServerCommunications {

    var message: String = "empty"
    private val client = OkHttpClient()
    val JSON: MediaType = "application/json; charset=utf-8".toMediaType()

    public fun getRequest(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                message = response.body?.string().toString()
            }
        })
    }

    fun postRequest(url: String, json: String) {
        val body = json.toRequestBody(JSON)
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                message = response.body?.string().toString()
            }
        })
    }
}