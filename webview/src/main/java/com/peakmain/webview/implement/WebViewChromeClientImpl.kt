package com.peakmain.webview.implement

import android.webkit.WebView
import com.peakmain.webview.abstracts.AbstractWebViewChromeClient
import com.peakmain.webview.callback.WebViewChromeClientCallback

/**
 * author ：Peakmain
 * createTime：2023/04/07
 * mail:2726449200@qq.com
 * describe：
 */
internal class WebViewChromeClientImpl(webViewChromeClientCallback: WebViewChromeClientCallback?) :
    AbstractWebViewChromeClient(webViewChromeClientCallback) {
    override fun initWebChromeClient(webView: WebView) {
        webView.webChromeClient = WebViewChromeClientImpl(webViewChromeClientCallback)
    }

}