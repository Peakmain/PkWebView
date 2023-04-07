package com.peakmain.webview.manager

import android.content.Context
import com.peakmain.webview.callback.DefaultWebViewChromeClientCallback
import com.peakmain.webview.callback.WebViewClientCallback
import com.peakmain.webview.callback.DefaultWebViewClientCallback
import com.peakmain.webview.callback.WebViewChromeClientCallback
import com.peakmain.webview.interfaces.InitWebViewConfig
import com.peakmain.webview.implement.init.DefaultInitWebViewSetting
import com.peakmain.webview.interfaces.InitWebViewSetting

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


        var mWebViewCount: Int = 3
        var userAgent: String = ""
        var mWebViewSetting: InitWebViewSetting = DefaultInitWebViewSetting()

        var mWebViewClientCallback: WebViewClientCallback = DefaultWebViewClientCallback()
        var mWebViewChromeClientCallback: WebViewChromeClientCallback =
            DefaultWebViewChromeClientCallback()

        fun apply(controller: WebViewController, P: WebViewParams) {
            controller.P = P
        }
    }

}