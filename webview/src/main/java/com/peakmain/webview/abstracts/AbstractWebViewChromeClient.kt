package com.peakmain.webview.abstracts

import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.peakmain.webview.callback.WebViewChromeClientCallback
import com.peakmain.webview.fragment.WebViewFragment
import com.peakmain.webview.manager.WebViewManager

/**
 * author ：Peakmain
 * createTime：2023/04/07
 * mail:2726449200@qq.com
 * describe：
 */
abstract class AbstractWebViewChromeClient(val webViewChromeClientCallback: WebViewChromeClientCallback?) :
    WebChromeClient() {
    private var fragment: WebViewFragment? = null
    abstract fun initWebChromeClient(webView: WebView)
    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        if (fragment == null) {
            fragment = WebViewManager.instance.getFragment()
        }
        fragment?.onProgressChanged(view, newProgress)
        webViewChromeClientCallback?.onProgressChanged(view, newProgress, fragment)
    }

    override fun onReceivedTitle(view: WebView?, title: String) {
        if (fragment == null) {
            fragment = WebViewManager.instance.getFragment()
        }
        fragment?.onReceivedTitle(view, title)
        webViewChromeClientCallback?.onReceivedTitle(view, title, fragment)
    }

}