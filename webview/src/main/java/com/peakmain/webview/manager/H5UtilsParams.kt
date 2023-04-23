package com.peakmain.webview.manager

import com.peakmain.webview.activity.WebViewActivity

/**
 * author ：Peakmain
 * createTime：2023/04/23
 * mail:2726449200@qq.com
 * describe：
 */
class H5UtilsParams private constructor() {
    var updateToolBarBar: ((String, WebViewActivity?) -> Unit)? = null
    var isShowToolBar: Boolean = true
    var updateStatusBar: ((String, WebViewActivity?) -> Unit)? = null

    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            H5UtilsParams()
        }
    }
}