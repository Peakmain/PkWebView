package com.peakmain.webview.manager

import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.text.TextUtils
import android.util.Log
import android.webkit.*

/**
 * author ：Peakmain
 * createTime：2023/04/04
 * mail:2726449200@qq.com
 * describe：
 */
internal class WebViewManager {
    companion object{
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            WebViewManager()
        }
    }
    fun initWebViewSetting(webView: WebView, userAgent: String? = null) {
        val webSettings: WebSettings = webView.settings
        WebView.setWebContentsDebuggingEnabled(true)
        webSettings.apply {
            //允许网页JavaScript
            javaScriptEnabled = true
            allowFileAccess = true
            useWideViewPort = true
            // 支持混合内容（https和http共存）
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            // 开启DOM存储API
            domStorageEnabled = true
            // 开启数据库存储API
            databaseEnabled = true
            //不允许WebView中跳转到其他链接
            setSupportMultipleWindows(false)
            setSupportZoom(false)
            builtInZoomControls = false
            cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        }
        if (!TextUtils.isEmpty(userAgent)) {
            webSettings.userAgentString = userAgent
        }
        CookieManager.setAcceptFileSchemeCookies(true)
        CookieManager.getInstance().setAcceptCookie(true)
    }

    fun initWebClient(webView: WebView) {
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                if (view != null && url != null){

                }
                Log.e("TAG","onPageStarted->>>>>")
                    //callback?.onPageStarted(view, url)
            }

            override fun onPageFinished(view: WebView, url: String) {
                //callback?.onPageFinished(view, url)
                Log.e("TAG","<<<<<-onPageFinished>")
            }

            override fun onLoadResource(view: WebView, url: String) {
                super.onLoadResource(view, url)
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                /* if (TextUtils.isEmpty(url) || callback == null) {
                    return super.shouldOverrideUrlLoading(view, url)
                }
               return callback.shouldOverrideUrlLoading(
                    view,
                    url
                ) || super.shouldOverrideUrlLoading(view, url)*/
                return super.shouldOverrideUrlLoading(view, url)
            }


            override fun onReceivedError(view: WebView, err: Int, des: String, url: String) {
                var des = des
                super.onReceivedError(view, err, des, url)
                when (err) {
                    ERROR_UNKNOWN -> des = "未知错误"
                    ERROR_HOST_LOOKUP -> des = "域名解析失败"
                    ERROR_UNSUPPORTED_AUTH_SCHEME -> des = "Scheme验证失败"
                    ERROR_AUTHENTICATION -> des = "验证失败"
                    ERROR_PROXY_AUTHENTICATION -> des = "代理验证失败"
                    ERROR_CONNECT -> des = "网络连接失败"
                    ERROR_IO -> des = "IO错误"
                    ERROR_TIMEOUT -> des = "请求超时"
                    ERROR_REDIRECT_LOOP -> des = "错误：重复重定向"
                    ERROR_UNSUPPORTED_SCHEME -> des = "不支持的Scheme"
                    ERROR_FAILED_SSL_HANDSHAKE -> des = "SSL handshake错误"
                    ERROR_BAD_URL -> des = "错误的地址"
                    ERROR_FILE -> des = "写入失败"
                    ERROR_FILE_NOT_FOUND -> des = "文件不存在"
                    ERROR_TOO_MANY_REQUESTS -> des = "错误：请求已到上限"
                }
                //view.loadUrl("javascript:document.body.innerHtml='';")
               // callback?.onReceivedError(view, err, des, url)
            }

            override fun onReceivedSslError(
                view: WebView,
                handler: SslErrorHandler,
                error: SslError
            ) {
                handler.proceed()
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
            }
        }
    }


    fun initWebChromeClient(webView: WebView) {
        webView.webChromeClient = object : WebChromeClient() {
            // file upload callback (Android 5.0 (API level 21) -- current) (public method)
            override fun onShowFileChooser(
                webView: WebView,
                filePathCallback: ValueCallback<Array<Uri>>,
                fileChooserParams: FileChooserParams
            ): Boolean {
                var acceptType = "image/*"
                val acceptTypes = fileChooserParams.acceptTypes
                if (acceptTypes != null && acceptTypes.isNotEmpty()) {
                    acceptType = acceptTypes[0]
                }
                //callback?.openFileInput(null, filePathCallback, acceptType)
                return true
            }

            fun openFileChooser(
                uploadMsg: ValueCallback<Uri>?,
                acceptType: String? = null,
                capture: String? = null
            ) {
                //callback?.openFileInput(uploadMsg, null, acceptType)
            }

            override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
                return super.onConsoleMessage(consoleMessage)
            }

            override fun onReceivedTitle(view: WebView, title: String) {
                //callback?.onReceivedTitle(title)
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                //callback?.onProgressChanged(view, newProgress)
            }

            override fun onJsAlert(
                view: WebView,
                url: String,
                message: String,
                result: JsResult
            ): Boolean {
                return super.onJsAlert(view, url, message, result)
            }
        }
    }
}