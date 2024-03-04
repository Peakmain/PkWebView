package com.peakmain.webview.manager

import android.content.Context
import android.content.MutableContextWrapper
import androidx.core.util.Pools
import com.peakmain.webview.view.PkWebView

/**
 * author ：Peakmain
 * createTime：2024/2/26
 * mail:2726449200@qq.com
 * describe：WebView 缓存管理类
 */
object WebViewCacheManager {
    private const val MAX_POOL_SIZE = 3
    private var sPool = Pools.SynchronizedPool<PkWebView>(MAX_POOL_SIZE)
    fun acquire(context: Context): PkWebView {
        var webView = sPool.acquire()
        if (webView == null) {
            val contextWrapper = MutableContextWrapper(context)
            webView = PkWebView(contextWrapper)
            sPool.release(webView)
        } else {
            val wrapper = webView.context as MutableContextWrapper
            wrapper.baseContext = context
        }
        return webView
    }

    fun destroy(webView: PkWebView?) {
        if (webView == null) return
        webView.release()
        val wrapper = webView.context as MutableContextWrapper
        wrapper.baseContext = wrapper.applicationContext
        sPool.release(webView)

    }
}