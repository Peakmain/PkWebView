package com.peakmain.webview.manager

import android.app.Application
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.io.File

/**
 * author ：Peakmain
 * createTime：2023/11/17
 * mail:2726449200@qq.com
 * describe：
 */
internal class OKHttpManager(application: Application) {
    private val webViewResourceCacheDir by lazy {
        File(application.cacheDir, "PkWebView")
    }
    fun createOkHttpClient():OkHttpClient{
        return OkHttpClient.Builder().cache(Cache(webViewResourceCacheDir, 500L * 1024 * 1024))
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
}