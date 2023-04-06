package com.peakmain.webview.manager

import android.content.Context
import com.peakmain.webview.interfaces.IWebViewConfig
import com.peakmain.webview.interfaces.implement.DefaultWebViewConfig

/**
 * author ：Peakmain
 * createTime：2023/04/06
 * mail:2726449200@qq.com
 * describe：
 */
internal class WebViewController {
    var P: WebViewController.WebViewParams? = null
        private set


    internal class WebViewParams(val context: Context) {
        var webViewConfig: IWebViewConfig = DefaultWebViewConfig()
        var userAgent: String = ""

        fun apply(controller: WebViewController, P: WebViewParams) {
            controller.P = P
        }
    }

}