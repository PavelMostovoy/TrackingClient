package com.mostovoi.trackingclient

import android.print.PrintDocumentAdapter
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

object ServerCommunications {
    private val client = OkHttpClient()
    val JSON: MediaType = "application/json; charset=utf-8".toMediaType()

    fun getRequest(url: String, callback:(String)-> Unit) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
              callback(response.body?.string().toString())
            }
        })
    }

    fun postRequest(url: String, json: String, callback: (String) -> Unit) {
        val body = json.toRequestBody(JSON)
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                callback(response.body?.string().toString())
            }
        })
    }
}