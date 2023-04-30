package com.peakmain.pkwebview.implements

import android.net.Uri
import android.text.TextUtils
import com.peakmain.pkwebview.bean.NewHybridModel
import com.peakmain.pkwebview.bean.WebViewModel
import com.peakmain.pkwebview.bean.WebViewModelEvent
import com.peakmain.webview.callback.HandleUrlParamsCallback
import com.peakmain.webview.utils.EncodeUtils
import com.peakmain.webview.utils.GsonUtils
import com.peakmain.webview.utils.LogWebViewUtils

/**
 * author ：Peakmain
 * createTime：2023/04/30
 * mail:2726449200@qq.com
 * describe：
 */
class HandlerUrlParamsImpl : HandleUrlParamsCallback<WebViewModelEvent> {
    override fun handleUrlParamsCallback(uri: Uri?, path: String?): WebViewModelEvent {
        val params = uri?.getQueryParameter("param")
        val webViewModelEvent = WebViewModelEvent()
        if (!TextUtils.isEmpty(params)) {
            val decodeParam: String =
                EncodeUtils.decode(params!!.replace(" ", "+"))
            LogWebViewUtils.e("PkWebView decodeParam:$decodeParam")
            if (TextUtils.equals("/jumpToWhere", path)) {
                val newHybridModel: NewHybridModel = GsonUtils.fromJson(
                    decodeParam,
                    NewHybridModel::class.java
                )
                webViewModelEvent.newHybridModel = newHybridModel
            } else {
                val webViewModel: WebViewModel =
                    GsonUtils.fromJson(decodeParam, WebViewModel::class.java)
                webViewModelEvent.webViewModel = webViewModel
            }
        }
        return webViewModelEvent
    }

}