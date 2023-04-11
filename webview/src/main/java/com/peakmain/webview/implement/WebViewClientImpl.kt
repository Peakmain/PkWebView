package com.peakmain.webview.implement

import android.webkit.WebView
import com.peakmain.webview.abstracts.AbstractWebViewClient
import com.peakmain.webview.callback.WebViewClientCallback

/**
 * author ：Peakmain
 * createTime：2023/04/07
 * mail:2726449200@qq.com
 * describe：
 */
internal class WebViewClientImpl(webViewClientCallback: WebViewClientCallback?) :
    AbstractWebViewClient(webViewClientCallback) {
    override fun initWebClient(webView: WebView) {
        val webViewClient = WebViewClientImpl(webViewClientCallback)
        webView.webViewClient = webViewClient
    }
}