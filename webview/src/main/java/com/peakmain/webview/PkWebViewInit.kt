package com.peakmain.webview

import android.content.Context
import com.peakmain.webview.manager.WebViewPool

/**
 * author ：Peakmain
 * createTime：2023/04/04
 * mail:2726449200@qq.com
 * describe：
 */
class PkWebViewInit private constructor() {
    companion object  {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            PkWebViewInit()
        }

    }

    fun init(context: Context?,userAgent:String="") {
        WebViewPool.instance.initWebViewPool(context,userAgent)
    }

}