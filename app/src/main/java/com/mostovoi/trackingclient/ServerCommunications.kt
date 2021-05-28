package com.mostovoi.trackingclient

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception


private val client = OkHttpClient()
val JSON: MediaType = "application/json; charset=utf-8".toMediaType()

fun getRequest(url: String, callback: (String) -> Unit) {
    val request = Request.Builder()
        .url(url)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            callback("something goes wrong")
        }
        override fun onResponse(call: Call, response: Response) {
            try {callback(response.body?.string().toString())}
            catch (e: Exception) {
                callback("$e")
            }

        }
    })
}

fun postRequest(url: String, json: String, callback: (String) -> Unit) {
    val body = json.toRequestBody(JSON)
    val request = Request.Builder()
        .url(url)
//        .addHeader("Accept", "application/json")
        .post(body)
        .build()
    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            callback("$e")
        }
        override fun onResponse(call: Call, response: Response) {
            callback(response.body?.string().toString())
        }
    })
}

fun putRequest(url: String, json: String, callback: (String) -> Unit) {
    val body = json.toRequestBody(JSON)
    val request = Request.Builder()
        .url(url)
        .addHeader("Accept", "application/json")
        .put(body)
        .build()
    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {}
        override fun onResponse(call: Call, response: Response) {
            callback(response.body?.string().toString())
        }
    })
}

fun deleteRequest(url: String, json: String, callback: (String) -> Unit) {
    val body = json.toRequestBody(JSON)
    val request = Request.Builder()
        .url(url)
        .delete(body)
        .build()
    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {}
        override fun onResponse(call: Call, response: Response) {
            callback(response.body?.string().toString())
        }
    })
}