package com.peakmain.webview.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.text.TextUtils
import android.webkit.*
import androidx.annotation.RequiresApi
import com.peakmain.basiclibrary.base.viewmodel.BaseViewModel
import com.peakmain.webview.callback.WebViewChromeClientCallback
import com.peakmain.webview.callback.WebViewClientCallback

/**
 * author ：Peakmain
 * createTime：2023/4/1
 * mail:2726449200@qq.com
 * describe：
 */
class WebViewModel : BaseViewModel() {
    override fun initModel() {

    }

    fun initWebViewSetting(webView: WebView, userAgent: String? = null) {
        val webSettings: WebSettings = webView.settings
        WebView.setWebContentsDebuggingEnabled(true)
        webSettings.apply {
            allowFileAccessFromFileURLs = true
            allowUniversalAccessFromFileURLs = true
            allowFileAccess = true
            useWideViewPort = true
            builtInZoomControls = false
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
            setSupportMultipleWindows(false)
            setSupportZoom(false)
            setRenderPriority(WebSettings.RenderPriority.HIGH)
        }
        if (!TextUtils.isEmpty(userAgent)) {
            webSettings.userAgentString = userAgent
        }
        if (Build.VERSION.SDK_INT >= 21) {
            webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        CookieManager.setAcceptFileSchemeCookies(true)
        CookieManager.getInstance().setAcceptCookie(true)
    }

    fun initWebClient(webView: WebView, callback: WebViewClientCallback? = null) {
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                if (view != null && url != null)
                    callback?.onPageStarted(view, url)
            }

            override fun onPageFinished(view: WebView, url: String) {
                callback?.onPageFinished(view, url)
            }

            override fun onLoadResource(view: WebView, url: String) {
                super.onLoadResource(view, url)
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (TextUtils.isEmpty(url) || callback == null) {
                    return super.shouldOverrideUrlLoading(view, url)
                }
                return callback.shouldOverrideUrlLoading(
                    view,
                    url
                ) || super.shouldOverrideUrlLoading(view, url)
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
                callback?.onReceivedError(view, err, des, url)
            }

            override fun onReceivedSslError(
                view: WebView,
                handler: SslErrorHandler,
                error: SslError
            ) {
                handler.proceed()
            }
        }
    }


    fun initWebChromeClient(webView: WebView, callback: WebViewChromeClientCallback?) {
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
                callback?.openFileInput(null, filePathCallback, acceptType)
                return true
            }

            fun openFileChooser(
                uploadMsg: ValueCallback<Uri>?,
                acceptType: String? = null,
                capture: String? = null
            ) {
                callback?.openFileInput(uploadMsg, null, acceptType)
            }

            override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
                return super.onConsoleMessage(consoleMessage)
            }

            override fun onReceivedTitle(view: WebView, title: String) {
                callback?.onReceivedTitle(title)
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                callback?.onProgressChanged(view, newProgress)
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