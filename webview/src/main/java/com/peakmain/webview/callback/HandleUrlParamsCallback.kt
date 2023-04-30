package com.peakmain.webview.callback

import android.net.Uri
import com.peakmain.webview.bean.WebViewEvent

/**
 * author ：Peakmain
 * createTime：2023/04/30
 * mail:2726449200@qq.com
 * describe：
 */
interface HandleUrlParamsCallback<T : WebViewEvent> {
    fun handleUrlParamsCallback(uri: Uri?, path: String?): T
}