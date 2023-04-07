package com.peakmain.webview.interfaces

import android.webkit.WebView

/**
 * author ：Peakmain
 * createTime：2023/04/06
 * mail:2726449200@qq.com
 * describe：
 */
interface IWebViewConfig {
    fun initWebClient(webView: WebView)
    fun initWebChromeClient(webView: WebView)

}