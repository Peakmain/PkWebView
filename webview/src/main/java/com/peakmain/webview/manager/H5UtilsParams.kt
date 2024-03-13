package com.peakmain.webview.manager

import android.view.View
import android.webkit.WebSettings
import com.peakmain.webview.activity.BaseWebViewActivity
import com.peakmain.webview.activity.WebViewActivity
import com.peakmain.webview.bean.WebViewEvent
import com.peakmain.webview.callback.HandleUrlParamsCallback
import com.peakmain.webview.fragment.WebViewFragment
import com.peakmain.webview.interfaces.LoadingViewConfig
import com.peakmain.webview.sealed.LoadingWebViewState
import com.peakmain.webview.view.PkWebView

/**
 * author ：Peakmain
 * createTime：2023/04/23
 * mail:2726449200@qq.com
 * describe：
 */
class H5UtilsParams private constructor() {
    var preLoadUrl: String = ""
    var mCacheMode: Int = WebSettings.LOAD_NO_CACHE
    var updateToolBarBar: ((String, BaseWebViewActivity?) -> Unit)? = null
    var isShowToolBar: Boolean = true
    var updateStatusBar: ((String, BaseWebViewActivity?) -> Unit)? = null
    var mLoadingWebViewState: LoadingWebViewState? = null
    var mLoadingViewConfig: LoadingViewConfig? = null
    var mHandleUrlParamsCallback: HandleUrlParamsCallback<out WebViewEvent>? = null
    var mHeadContentView: View? = null
    var mHeadContentViewId: Int = 0
    var mHeadViewBlock: ((View) -> Unit)? = null
    var mExecuteJsPair: Triple<String, String, ((PkWebView?, WebViewFragment?) -> Unit)?>? = null
    var mCommonWeResourceResponsePair: Triple<String, String, ((String) -> Boolean)?>? = null

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
        mCacheMode = WebSettings.LOAD_DEFAULT
        mExecuteJsPair = null
    }
}