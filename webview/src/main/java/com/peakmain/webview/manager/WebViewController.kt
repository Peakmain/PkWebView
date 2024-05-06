package com.peakmain.webview.manager

import android.app.Application
import android.view.View
import com.peakmain.webview.R
import com.peakmain.webview.bean.WebViewEvent
import com.peakmain.webview.callback.DefaultWebViewChromeClientCallback
import com.peakmain.webview.callback.DefaultWebViewClientCallback
import com.peakmain.webview.callback.HandleUrlParamsCallback
import com.peakmain.webview.callback.WebViewChromeClientCallback
import com.peakmain.webview.callback.WebViewClientCallback
import com.peakmain.webview.implement.init.DefaultInitWebViewSetting
import com.peakmain.webview.implement.loading.ProgressLoadingConfigImpl
import com.peakmain.webview.interfaces.InitWebViewSetting
import com.peakmain.webview.interfaces.LoadingViewConfig
import com.peakmain.webview.sealed.LoadingWebViewState

/**
 * author ：Peakmain
 * createTime：2023/04/06
 * mail:2726449200@qq.com
 * describe：
 */
class WebViewController {
    var P: WebViewParams? = null
        private set


    class WebViewParams(val application: Application) {


        var notCacheUrlArray: Array<String>? = null
        var mHandleUrlParamsCallback: HandleUrlParamsCallback<out WebViewEvent>? = null
        var mWebViewCount: Int = 3
        var userAgent: String = ""
        var mWebViewSetting: InitWebViewSetting = DefaultInitWebViewSetting()

        var mWebViewClientCallback: WebViewClientCallback = DefaultWebViewClientCallback()
        var mWebViewChromeClientCallback: WebViewChromeClientCallback =
            DefaultWebViewChromeClientCallback()

        //默认不显示Loading
        var mLoadingWebViewState: LoadingWebViewState = LoadingWebViewState.NotLoading
        var mLoadingViewConfig: LoadingViewConfig = ProgressLoadingConfigImpl()
        var mNoNetWorkView: View? = null
        var mNoNetWorkViewId: Int = R.layout.webview_no_network
        var mNetWorkViewBlock: ((View?, View?, String?) -> Unit)? = null
        var mEntities: Array<out Class<*>>? = null
        fun apply(controller: WebViewController, P: WebViewParams) {
            controller.P = P
        }
    }

}