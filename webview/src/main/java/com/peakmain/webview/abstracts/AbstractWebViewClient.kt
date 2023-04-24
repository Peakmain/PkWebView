package com.peakmain.webview.abstracts

import android.graphics.Bitmap
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.peakmain.webview.callback.WebViewClientCallback
import com.peakmain.webview.fragment.WebViewFragment
import com.peakmain.webview.manager.WebViewManager

/**
 * author ：Peakmain
 * createTime：2023/04/06
 * mail:2726449200@qq.com
 * describe：
 */
abstract class AbstractWebViewClient constructor(val webViewClientCallback: WebViewClientCallback?) :
    WebViewClient() {
    private var fragment: WebViewFragment? = null
    abstract fun initWebClient(webView: WebView)
    override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
        getWebViewFragment()
        fragment?.onPageStarted(view, url)
        webViewClientCallback?.onPageStarted(view, url, fragment)
    }

    override fun onPageFinished(view: WebView, url: String) {
        getWebViewFragment()
        fragment?.onPageFinished(view, url)
        webViewClientCallback?.onPageFinished(
            view,
            url,
            fragment
        )

    }

    override fun onReceivedError(
        view: WebView?,
        errorCode: Int,
        description: String?,
        failingUrl: String?
    ) {
        getWebViewFragment()
        fragment?.onReceivedError(view, errorCode, description, failingUrl)
        webViewClientCallback?.onReceivedError(view, errorCode, description, failingUrl, fragment)
    }

    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        getWebViewFragment()
        fragment?.shouldOverrideUrlLoading(view, url)
        return webViewClientCallback?.shouldOverrideUrlLoading(
            view,
            url,
            fragment
        ) ?: super.shouldOverrideUrlLoading(view, url)
    }

    private fun getWebViewFragment() {
        if (fragment == null) {
            fragment = WebViewManager.instance.getFragment()
        }
    }

    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest
    ): WebResourceResponse? {
        getWebViewFragment()
        val url = request.url.toString()
        if (!url.startsWith("http") || !url.startsWith("https"))
            return super.shouldInterceptRequest(view, request)
        if (request.requestHeaders.containsKey("noImage") &&
            request.requestHeaders["noImage"] != null
        ) {
            return WebResourceResponse("", "", null)
        }
        fragment?.shouldInterceptRequest(view, request)
        return webViewClientCallback?.shouldInterceptRequest(view, request)
            ?: super.shouldInterceptRequest(view, request)
    }
}