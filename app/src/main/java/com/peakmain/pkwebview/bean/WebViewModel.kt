package com.peakmain.pkwebview.bean

import com.peakmain.webview.bean.WebViewEvent

/**
 * author ：Peakmain
 * createTime：2023/04/30
 * mail:2726449200@qq.com
 * describe：
 */
data class WebViewModelEvent(
    var webViewModel: WebViewModel? = null,
    var newHybridModel: NewHybridModel? = null
) : WebViewEvent()

data class WebViewModel (
    var status: Int = 1,
    var data: HashMap<String, String>?,
    var callId: String = "" //用于给前端的协议

)