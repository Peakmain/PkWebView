package com.peakmain.webview.manager

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.text.TextUtils
import android.util.Log
import android.webkit.*
import com.peakmain.webview.activity.WebViewActivity
import com.peakmain.webview.bean.WebViewConfigBean
import com.peakmain.webview.callback.WebViewClientCallback
import com.peakmain.webview.helper.WebViewHelper
import com.peakmain.webview.interfaces.IWebViewConfig
import com.peakmain.webview.interfaces.implement.DefaultWebViewConfig

/**
 * author ：Peakmain
 * createTime：2023/04/04
 * mail:2726449200@qq.com
 * describe：
 */
internal class WebViewManager {
    var mWebViewConfig: IWebViewConfig = DefaultWebViewConfig()

    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            WebViewManager()
        }
    }

}