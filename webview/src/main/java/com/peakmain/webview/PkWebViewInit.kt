package com.peakmain.webview

import android.content.Context
import com.peakmain.webview.interfaces.IWebViewConfig
import com.peakmain.webview.interfaces.IWebViewSetting
import com.peakmain.webview.manager.WebViewController
import com.peakmain.webview.manager.WebViewPool

/**
 * author ：Peakmain
 * createTime：2023/04/04
 * mail:2726449200@qq.com
 * describe：WebView初始化类
 */
class PkWebViewInit private constructor(
    private val context: Context,
    private val userAgent: String
) {
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

        fun setWebViewSetting(webViewSetting: IWebViewSetting):Builder {
           P.mWebViewSetting=webViewSetting
            return this
        }

        fun setWebViewConfig(webViewConfig: IWebViewConfig): Builder {
            P.webViewConfig = webViewConfig
            return this
        }

        fun setUserAgent(userAgent: String = ""): Builder {
            P.userAgent = userAgent
            return this
        }

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
            val pkWebViewInit = PkWebViewInit(P.context, P.userAgent)
            P.apply(pkWebViewInit.mWebViewController, P)
            mPkWebViewInit = pkWebViewInit
            return pkWebViewInit
        }
    }

}