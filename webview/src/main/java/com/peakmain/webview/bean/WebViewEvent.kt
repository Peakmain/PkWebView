package com.peakmain.webview.bean

import android.content.Context
import android.webkit.WebView

/**
 * author ：Peakmain
 * createTime：2023/04/26
 * mail:2726449200@qq.com
 * describe：
 */
data class WebViewEvent(
    var webView: WebView?,
    var context: Context?,
    var webViewModel: WebViewModel? = null
)

data class WebViewModel(
    val status: Int = 1,
    val data: HashMap<String, String>,
    val callId: String = "" //用于给前端的协议
)