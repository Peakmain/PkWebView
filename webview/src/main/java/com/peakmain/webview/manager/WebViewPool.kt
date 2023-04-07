package com.peakmain.webview.manager

import android.view.ViewGroup
import com.peakmain.webview.implement.WebViewChromeClientImpl
import com.peakmain.webview.implement.WebViewClientImpl
import com.peakmain.webview.view.PkWebView

/**
 * author ：Peakmain
 * createTime：2023/04/04
 * mail:2726449200@qq.com
 * describe：WebView缓存池管理
 */
internal class WebViewPool private constructor() {
    private lateinit var mUserAgent: String
    private lateinit var mWebViewPool: Array<PkWebView?>
    lateinit var mParams: WebViewController.WebViewParams

    companion object {
        private var WEB_VIEW_COUNT = 3
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            WebViewPool()
        }

    }

    /**
     * 初始化WebView池
     */
    fun initWebViewPool(params: WebViewController.WebViewParams?) {
        if (params == null) return
        WEB_VIEW_COUNT = params.mWebViewCount
        mWebViewPool = arrayOfNulls(WEB_VIEW_COUNT)
        mUserAgent = params.userAgent
        mParams = params
        for (i in 0 until WEB_VIEW_COUNT) {
            mWebViewPool[i] = createWebView(params, mUserAgent)
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
        checkIsInitialized()
        webView?.apply {
            stopLoading()
            removeAllViews()
            clearHistory()
            destroy()
            (parent as ViewGroup?)?.removeView(this)
            for (i in 0 until WEB_VIEW_COUNT) {
                if (mWebViewPool[i] == null) {
                    mWebViewPool[i] = createWebView(mParams, mUserAgent)
                    return
                }
            }
        }

    }

    private fun createWebView(
        params: WebViewController.WebViewParams, userAgent: String
    ): PkWebView {
        val webView = PkWebView(params.context)
        params.apply {
            mWebViewSetting.initWebViewSetting(webView, userAgent)
            WebViewClientImpl(params.mWebViewClientCallback).initWebClient(webView)
            WebViewChromeClientImpl(params.mWebViewChromeClientCallback)
                .initWebChromeClient(webView)
        }
        return webView
    }
}