package com.peakmain.webview.manager.cache

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.MessageQueue
import com.peakmain.webview.manager.WebViewPool
import com.peakmain.webview.utils.LogWebViewUtils
import com.peakmain.webview.view.PkWebView

/**
 * author ：Peakmain
 * createTime：2024/3/11
 * mail:2726449200@qq.com
 * describe：外部WebView的管理，提供给外部使用
 */
class CacheWebViewManager private constructor() {

    companion object {
        @JvmStatic
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            CacheWebViewManager()
        }

    }


    private var webViewPool = WebViewPool.instance

    fun getWebView(context: Context?): PkWebView? {
        return webViewPool.getWebView(context)
    }

    fun releaseWebView(webView: PkWebView?) {
        return webViewPool.releaseWebView(webView)
    }

    /**
     * 预加载
     */
    fun preLoadUrl(context: Context?, url: String) {
        Looper.myQueue().addIdleHandler(object : MessageQueue.IdleHandler {
            override fun queueIdle(): Boolean {
                val webView = getWebView(context) ?: return true
                webView.preLoadUrl(url)
                return false
            }
        })
    }
}