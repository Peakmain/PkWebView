package com.peakmain.webview.manager

import android.app.Application
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
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

    fun getWebResourceResponse(webView: WebView, request: WebResourceRequest): WebResourceResponse? {
        val url = request.url.toString()
        val requestBuilder = Request.Builder().url(url).method(request.method, null)
        val requestHeaders = request.requestHeaders
        requestHeaders.takeIf {
            !requestHeaders.isNullOrEmpty()
        }?.forEach {
            requestBuilder.addHeader(it.key, it.value)
        }
        val response = mOkHttpClient.newCall(requestBuilder.build()).execute()
        if (response.code != 200) return null
        val body = response.body
        body?.let {
            val mimeType = response.header(
                "content-type", body.contentType()?.type
            )
            val encoding = response.header(
                "content-encoding",
                "utf-8"
            )
            val responseHeaders = mutableMapOf<String, String>()
            for (header in response.headers) {
                responseHeaders[header.first] = header.second
            }
            var message = response.message
            if (message.isBlank()) {
                message = "OK"
            }
            val resourceResponse =
                WebResourceResponse(mimeType, encoding, body.byteStream())
            resourceResponse.responseHeaders = responseHeaders
            resourceResponse.setStatusCodeAndReasonPhrase(response.code, message)
            return resourceResponse
        }
        return null
    }

    fun init(application: Application) {
        this.mApplication = application
    }
}