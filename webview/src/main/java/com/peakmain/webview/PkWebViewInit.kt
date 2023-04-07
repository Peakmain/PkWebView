package com.peakmain.webview

import android.content.Context
import com.peakmain.webview.callback.WebViewChromeClientCallback
import com.peakmain.webview.callback.WebViewClientCallback
import com.peakmain.webview.interfaces.InitWebViewConfig
import com.peakmain.webview.interfaces.InitWebViewSetting
import com.peakmain.webview.manager.WebViewController
import com.peakmain.webview.manager.WebViewPool

/**
 * author ：Peakmain
 * createTime：2023/04/04
 * mail:2726449200@qq.com
 * describe：WebView初始化类
 */
class PkWebViewInit private constructor() {
    private var mWebViewController: WebViewController = WebViewController()

    fun initPool() {
        WebViewPool.instance.initWebViewPool(mWebViewController.P)
    }

    class Builder constructor(context: Context) {
        private val P: WebViewController.WebViewParams
        private var mPkWebViewInit: PkWebViewInit? = null

        init {
            P = WebViewController.WebViewParams(context)
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
            if (mPkWebViewInit == null) {
                create()
            }
            mPkWebViewInit!!.initPool()
        }

        private fun create(): PkWebViewInit {
            val pkWebViewInit = PkWebViewInit()
            P.apply(pkWebViewInit.mWebViewController, P)
            mPkWebViewInit = pkWebViewInit
            return pkWebViewInit
        }
    }

}