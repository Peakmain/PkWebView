package com.peakmain.webview.manager

import android.app.Application
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import com.peakmain.webview.utils.WebViewUtils
import okhttp3.Cache
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.IOException

/**
 * author ：Peakmain
 * createTime：2023/11/15
 * mail:2726449200@qq.com
 * describe：
 */
class InterceptRequestManager private constructor() {
    private lateinit var mApplication: Application
    private val mOkHttpClient by lazy {
        OKHttpManager(mApplication).createOkHttpClient()
    }


    companion object {
        @JvmStatic
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            InterceptRequestManager()
        }
    }

    fun getLocalWebResourceResponse(fileName: String, mimeType: String): WebResourceResponse? {
        val inputStream = mApplication.assets.open(fileName)
        return WebResourceResponse(mimeType, "utf-8", inputStream)
    }

    fun getWebResourceResponse(request: WebResourceRequest, callback: (WebResourceResponse?) -> Unit) {
        val url = request.url.toString()
        val requestBuilder = Request.Builder().url(url).method(request.method, null)
        val requestHeaders = request.requestHeaders
        requestHeaders?.forEach { requestBuilder.addHeader(it.key, it.value) }

        mOkHttpClient.newCall(requestBuilder.build()).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // 处理网络请求失败的情况
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    callback(null)
                    return
                }

                response.body?.let { body ->
                    val mimeType = response.header("content-type", body.contentType()?.type)
                    val encoding = response.header("content-encoding", "utf-8")

                    val responseHeaders = mutableMapOf<String, String>()
                    for (header in response.headers) {
                        responseHeaders[header.first] = header.second
                    }

                    val message = response.message.ifBlank { "OK" }
                    val webResourceResponse = WebResourceResponse(mimeType, encoding, body.byteStream())
                    webResourceResponse.responseHeaders = responseHeaders
                    webResourceResponse.setStatusCodeAndReasonPhrase(response.code, message)

                    callback(webResourceResponse)
                } ?: callback(null)
            }
        })
    }

    fun init(application: Application) {
        this.mApplication = application
    }
}