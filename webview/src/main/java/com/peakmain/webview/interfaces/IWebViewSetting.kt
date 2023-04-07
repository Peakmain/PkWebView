package com.peakmain.webview.interfaces

import android.webkit.WebView

/**
 * author ：Peakmain
 * createTime：2023/04/07
 * mail:2726449200@qq.com
 * describe：
 */
interface IWebViewSetting {
    fun initWebViewSetting(webView: WebView, userAgent: String? = null)
}