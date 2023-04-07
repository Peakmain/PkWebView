package com.peakmain.webview.implement

import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import com.peakmain.webview.callback.WebViewClientCallback
import com.peakmain.webview.fragment.WebViewFragment
import com.peakmain.webview.manager.WebViewManager

/**
 * author ：Peakmain
 * createTime：2023/04/06
 * mail:2726449200@qq.com
 * describe：
 */
abstract class BaseWebViewClient constructor(val webViewClientCallback: WebViewClientCallback?) :
    WebViewClient() {
    private var fragment: WebViewFragment? = null
    override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
        if (fragment == null) {
            fragment = WebViewManager.instance.getFragment()
        }
        fragment?.onPageStarted(view, url)
        webViewClientCallback?.onPageStarted(view, url, fragment)
    }

    override fun onPageFinished(view: WebView, url: String) {
        if (fragment == null) {
            fragment = WebViewManager.instance.getFragment()
        }
        fragment?.onPageFinished(view, url)
        webViewClientCallback?.onPageFinished(
            view,
            url,
            fragment
        )

    }

    override fun onReceivedError(
        view: WebView?,
        errorCode: Int,
        description: String?,
        failingUrl: String?
    ) {
        if (fragment == null) {
            fragment = WebViewManager.instance.getFragment()
        }
        fragment?.onReceivedError(view, errorCode, description, failingUrl)
        webViewClientCallback?. onReceivedError(view, errorCode, description, failingUrl, fragment)
    }

    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        if (fragment == null) {
            fragment = WebViewManager.instance.getFragment()
        }
        fragment?.shouldOverrideUrlLoading(view, url)
        return webViewClientCallback?.shouldOverrideUrlLoading(
            view,
            url,
            fragment
        )?:super.shouldOverrideUrlLoading(view,url)
    }
}