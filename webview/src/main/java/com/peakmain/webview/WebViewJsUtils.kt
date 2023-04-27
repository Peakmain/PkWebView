package com.peakmain.webview

import android.webkit.WebView
import com.peakmain.webview.utils.EncodeUtils
import com.peakmain.webview.utils.LogWebViewUtils

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
        val sb = StringBuilder()
        if (datas.isNotEmpty()) {
            datas.forEachIndexed { index, s ->
                if (index > 0) {
                    sb.append(", ")
                }
                sb.append("'")
                    .append(EncodeUtils.encode(datas[index]))
                    .append("'");
            }
        }
        val jsCode = String.format("Prius.%s(%s)", method, sb.toString())
        webView.evaluateJavascript(jsCode) { result ->
            // 处理执行结果
            LogWebViewUtils.e("executeJs:$result")
        }
        return true
    }
}