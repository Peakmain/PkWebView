package com.peakmain.pkwebview.handle

import android.text.TextUtils
import com.peakmain.pkwebview.BuildConfig
import com.peakmain.webview.H5Utils
import com.peakmain.webview.annotation.Handler
import com.peakmain.webview.annotation.HandlerMethod
import com.peakmain.webview.bean.WebViewConfigBean
import com.peakmain.webview.bean.WebViewEvent
import com.peakmain.webview.sealed.HandleResult
import java.net.URLDecoder

/**
 * author ：Peakmain
 * createTime：2023/4/28
 * mail:2726449200@qq.com
 * describe：
 */
@Handler(scheme = BuildConfig.scheme, authority = ["page"])
class PageActionHandle {
    @HandlerMethod(path = BuildConfig.jumpWherePath)
    fun gotoWhere(event: WebViewEvent): HandleResult {
        val context = event.context
        val newHybridData = event.newHybridModel?.data
        if(newHybridData!=null&& !TextUtils.isEmpty(newHybridData.data.url) ){
            var url: String? = newHybridData.data.url
            url = URLDecoder.decode(url, "utf-8")
            H5Utils().startActivity(context, WebViewConfigBean(url))
        }
        return HandleResult.Consumed
    }
}