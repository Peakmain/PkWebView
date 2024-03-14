package com.peakmain.webview.manager.cache

import android.content.Context
import android.os.Handler
import android.os.Looper
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
        private const val MAX_WAIT_TIME_MS = 5000 // 最大等待时间，单位：毫秒

    }

    private val mHandler = Handler(Looper.getMainLooper())

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
        val startTime = System.currentTimeMillis();
        mHandler.post(object : Runnable {
            override fun run() {
                val webView = getWebView(context)
                if (webView != null) {
                    webView.preLoadUrl(url)
                    return
                }
                if (System.currentTimeMillis() - startTime < MAX_WAIT_TIME_MS) {
                    mHandler.postDelayed(this, 100)
                } else {
                    mHandler.removeCallbacks(this)
                    LogWebViewUtils.e("pkWebView 预加载${url}失败,原因:未获取到可用的WebView")
                }
            }

        })
    }
}