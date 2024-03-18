package com.peakmain.webview

import android.app.Application
import android.os.Looper
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import com.peakmain.webview.bean.WebViewEvent
import com.peakmain.webview.callback.HandleUrlParamsCallback
import com.peakmain.webview.callback.WebViewChromeClientCallback
import com.peakmain.webview.callback.WebViewClientCallback
import com.peakmain.webview.interfaces.InitWebViewSetting
import com.peakmain.webview.interfaces.LoadingViewConfig
import com.peakmain.webview.manager.InterceptRequestManager
import com.peakmain.webview.manager.WebViewController
import com.peakmain.webview.manager.WebViewPool
import com.peakmain.webview.sealed.LoadingWebViewState

/**
 * author ：Peakmain
 * createTime：2023/04/04
 * mail:2726449200@qq.com
 * describe：WebView初始化类
 */
@MainThread
class PkWebViewInit private constructor() {
    private var mWebViewController: WebViewController = WebViewController()

    fun initPool() {
        WebViewPool.instance.initWebViewPool(mWebViewController.P)
    }

    class Builder constructor(application: Application) {
        private val P: WebViewController.WebViewParams
        private var mPkWebViewInit: PkWebViewInit? = null

        init {
            P = WebViewController.WebViewParams(application)
            InterceptRequestManager.instance.init(application)
        }

        /**
         * 设置WebViewSetting
         */
        fun setWebViewSetting(webViewSetting: InitWebViewSetting): Builder {
            P.mWebViewSetting = webViewSetting
            return this
        }

        fun setWebViewClient(webViewClientCallback: WebViewClientCallback): Builder {
            P.mWebViewClientCallback = webViewClientCallback
            return this
        }

        fun setWebViewChromeClient(webViewChromeClientCallback: WebViewChromeClientCallback): Builder {
            P.mWebViewChromeClientCallback = webViewChromeClientCallback
            return this
        }

        fun setLoadingView(loadingViewConfig: LoadingViewConfig): Builder {
            P.mLoadingViewConfig = loadingViewConfig
            P.mLoadingWebViewState = LoadingWebViewState.CustomLoadingStyle
            return this
        }

        fun setLoadingWebViewState(loadingWebViewState: LoadingWebViewState): Builder {
            P.mLoadingWebViewState = loadingWebViewState
            return this
        }

        fun setNoNetWorkView(
            view: View,
            noNetWorkViewBlock: ((View?, View?, String?) -> Unit)? = null,
        ): Builder {
            P.mNoNetWorkView = view
            P.mNoNetWorkViewId = 0
            P.mNetWorkViewBlock = noNetWorkViewBlock
            return this
        }

        fun setNoNetWorkView(
            @LayoutRes viewIdRes: Int,
            noNetWorkViewBlock: ((View?, View?, String?) -> Unit)?,
        ): Builder {
            P.mNoNetWorkView = null
            P.mNoNetWorkViewId = viewIdRes
            P.mNetWorkViewBlock = noNetWorkViewBlock
            return this
        }

        /**
         * 设置UserAgent
         */
        fun setUserAgent(userAgent: String = ""): Builder {
            P.userAgent = userAgent
            return this
        }

        /**
         * 设置webView的count
         */
        fun setWebViewCount(count: Int): Builder {
            P.mWebViewCount = count
            return this
        }

        fun build() {
            Looper.myQueue().addIdleHandler {
                if (mPkWebViewInit == null) {
                    create()
                }
                mPkWebViewInit!!.initPool()
                false
            }
        }

        private fun create(): PkWebViewInit {
            val pkWebViewInit = PkWebViewInit()
            P.apply(pkWebViewInit.mWebViewController, P)
            mPkWebViewInit = pkWebViewInit
            return pkWebViewInit
        }

        /**
         * 设置全局拦截url回调
         */
        fun setHandleUrlParamsCallback(
            handleUrlParamsCallback:
            HandleUrlParamsCallback<out WebViewEvent>,
        ): Builder {
            P.mHandleUrlParamsCallback = handleUrlParamsCallback
            return this
        }

        fun registerEntities(vararg entities: Class<*>): Builder {
            P.mEntities = entities
            return this
        }
    }

}