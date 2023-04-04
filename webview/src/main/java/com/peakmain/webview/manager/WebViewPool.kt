package com.peakmain.webview.manager

import android.content.Context
import android.view.ViewGroup
import com.peakmain.webview.view.PkWebView

/**
 * author ：Peakmain
 * createTime：2023/04/04
 * mail:2726449200@qq.com
 * describe：WebView缓存池管理
 */
internal class WebViewPool private constructor() {
    private lateinit var mWebViewPool: Array<PkWebView?>

    companion object {
        const val WEB_VIEW_COUNT = 3
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            WebViewPool()
        }

    }

    /**
     * 初始化WebView池
     */
    fun initWebViewPool(context: Context?, userAgent: String = "") {
        mWebViewPool = arrayOfNulls(WEB_VIEW_COUNT)
        if (context == null) return
        for (i in 0 until WEB_VIEW_COUNT) {
            mWebViewPool[i] = createWebView(context, userAgent)
        }
    }

    /**
     * 获取webView
     */
    fun getWebView(): PkWebView? {
        checkIsInitialized()
        for (i in 0 until WEB_VIEW_COUNT) {
            if (mWebViewPool[i] != null) {
                val webView = mWebViewPool[i]
                mWebViewPool[i] = null
                return webView
            }
        }
        return null
    }

    private fun checkIsInitialized() {
        if (!::mWebViewPool.isInitialized) {
            throw UninitializedPropertyAccessException("Please call the PkWebViewInit.init method for initialization in the Application")
        }
    }

    /**
     * Activity销毁时需要释放当前WebView
     */
    fun releaseWebView(webView: PkWebView?) {
        if (webView == null) return
        webView.removeAllViews()
        webView.clearHistory()
        ((webView.parent) as ViewGroup?)?.removeView(webView)
        for (i in 0 until WEB_VIEW_COUNT) {
            if (mWebViewPool[i] == null) {
                mWebViewPool[i] = webView
                return
            }
        }
    }

    private fun createWebView(context: Context, userAgent: String): PkWebView? {
        val webView = PkWebView(context)
        WebViewManager.instance.apply {
            initWebViewSetting(webView, userAgent)
            initWebChromeClient(webView)
            initWebClient(webView)
        }
        return webView
    }
}