package com.peakmain.webview.manager

import android.content.Context
import com.peakmain.webview.interfaces.IWebViewConfig
import com.peakmain.webview.implement.DefaultWebViewConfig
import com.peakmain.webview.implement.settings.DefaultWebViewSetting
import com.peakmain.webview.interfaces.IWebViewSetting

/**
 * author ：Peakmain
 * createTime：2023/04/06
 * mail:2726449200@qq.com
 * describe：
 */
internal class WebViewController {
    var P: WebViewParams? = null
        private set


    class WebViewParams(val context: Context) {
        var mWebViewSetting: IWebViewSetting = DefaultWebViewSetting()
        var mWebViewCount: Int = 3
        var webViewConfig: IWebViewConfig = DefaultWebViewConfig()
        var userAgent: String = ""

        fun apply(controller: WebViewController, P: WebViewParams) {
            controller.P = P
        }
    }

}