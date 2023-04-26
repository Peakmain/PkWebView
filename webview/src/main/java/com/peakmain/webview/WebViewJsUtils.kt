package com.peakmain.webview

import android.webkit.WebView
import com.peakmain.webview.utils.EncodeUtils

/**
 * author ：Peakmain
 * createTime：2023/04/26
 * mail:2726449200@qq.com
 * describe：
 */
class WebViewJsUtils private constructor() {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            WebViewJsUtils()
        }
    }

    fun executeJs(webView: WebView?, method: String, vararg datas: String): Boolean {
        if (webView == null) return false
        val sb = StringBuffer()
        if (datas.isNotEmpty()) {
            datas.forEachIndexed{index, s ->
                if(index>0){
                    sb.append(", ")
                }
                sb.append("'")
                    .append(EncodeUtils.encode(datas[index]))
                    .append("'");
            }
        }
    }
}