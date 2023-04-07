package com.peakmain.webview.implement

import android.net.Uri
import android.webkit.*
import com.peakmain.webview.interfaces.InitWebViewConfig

/**
 * author ：Peakmain
 * createTime：2023/04/06
 * mail:2726449200@qq.com
 * describe：
 */
class DefaultWebViewConfig : InitWebViewConfig {
    override fun initWebChromeClient(webView: WebView) {
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