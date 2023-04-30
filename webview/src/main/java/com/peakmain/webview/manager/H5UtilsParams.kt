package com.peakmain.webview.manager

import android.net.Uri
import com.peakmain.webview.activity.WebViewActivity
import com.peakmain.webview.bean.WebViewEvent
import com.peakmain.webview.callback.HandleUrlParamsCallback
import com.peakmain.webview.interfaces.LoadingViewConfig
import com.peakmain.webview.sealed.LoadingWebViewState

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
    var mLoadingWebViewState: LoadingWebViewState? = null
    var mLoadingViewConfig: LoadingViewConfig? = null
    var mHandleUrlParamsCallback: HandleUrlParamsCallback<out WebViewEvent>? = null

    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            H5UtilsParams()
        }
    }

    fun clear() {
        updateToolBarBar = null
        isShowToolBar = true
        updateToolBarBar = null
        mLoadingViewConfig = null
        mLoadingWebViewState = null
    }
}