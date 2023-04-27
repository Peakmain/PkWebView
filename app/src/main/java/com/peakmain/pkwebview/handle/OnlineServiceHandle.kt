package com.peakmain.pkwebview.handle

import com.peakmain.pkwebview.BuildConfig
import com.peakmain.webview.WebViewJsUtils
import com.peakmain.webview.annotation.Handler
import com.peakmain.webview.annotation.HandlerMethod
import com.peakmain.webview.bean.WebViewEvent
import com.peakmain.webview.sealed.HandleResult
import com.peakmain.webview.utils.GsonUtils
import com.peakmain.webview.utils.LogWebViewUtils

/**
 * author ：Peakmain
 * createTime：2023/02/28
 * mail:2726449200@qq.com
 * describe：
 */
@Handler(scheme = BuildConfig.scheme, authority = ["onlineService"])
class OnlineServiceHandle {
    @HandlerMethod(path = BuildConfig.path)
    fun webPolicyAlert(event: WebViewEvent): HandleResult {
        val context = event.context
        val model = event.webViewModel
        val webView = event.webView
        if (webView == null || model == null) return HandleResult.Consumed
        LogWebViewUtils.e("webView.getUrl():" + webView.url)
        val data = model.data
        model.callId = ""
        //WebViewJsUtils.getInstance().executeJs(webView, GsonUtils.toJson(model));
        //保存数据
        return HandleResult.Consumed
    }

}