package com.peakmain.webview.bean

import android.content.Context
import android.webkit.WebView
import com.peakmain.pkwebview.bean.NewHybridModel

/**
 * author ：Peakmain
 * createTime：2023/04/26
 * mail:2726449200@qq.com
 * describe：
 */
data class WebViewEvent(
    var webView: WebView?,
    var context: Context?,
    var webViewModel: WebViewModel? = null,
    var newHybridModel: NewHybridModel?=null
)
data class WebViewModel(
    var status: Int = 1,
    var data: HashMap<String, String>?,
    var callId: String = "" //用于给前端的协议
)