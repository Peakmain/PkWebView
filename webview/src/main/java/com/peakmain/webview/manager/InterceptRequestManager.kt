package com.peakmain.webview.manager

import android.app.Application
import android.graphics.Bitmap
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import okhttp3.Cache
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.concurrent.Callable
import java.util.concurrent.CancellationException
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

/**
 * author ：Peakmain
 * createTime：2023/11/15
 * mail:2726449200@qq.com
 * describe：
 */
class InterceptRequestManager private constructor() {
    private lateinit var mApplication: Application
    private val webViewResourceCacheDir by lazy {
        File(mApplication.cacheDir, "PkWebView")
    }

    private val mOkHttpClient by lazy {
        OkHttpClient.Builder().cache(Cache(webViewResourceCacheDir, 500L * 1024 * 1024))
            .followRedirects(false)
            .followSslRedirects(false)
            .addNetworkInterceptor(getWebViewCacheInterceptor())
            .build()
    }

    private fun getWebViewCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            response.newBuilder().removeHeader("pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", "max-age=" + (360L * 24 * 60 * 60))
                .build()
        }
    }

    companion object {
        @JvmStatic
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            InterceptRequestManager()
        }
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

                    val message = if (response.message.isBlank()) "OK" else response.message
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