package com.peakmain.pkwebview

import android.webkit.WebView
import com.peakmain.webview.interfaces.IWebViewConfig

/**
 * author ：Peakmain
 * createTime：2023/04/06
 * mail:2726449200@qq.com
 * describe：
 */
class ReplaceWebViewConfig:IWebViewConfig {
    override fun initWebViewSetting(webView: WebView, userAgent: String?) {
    }

    override fun initWebClient(webView: WebView) {
    }

    override fun initWebChromeClient(webView: WebView) {
    }
}