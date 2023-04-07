package com.peakmain.pkwebview

import android.webkit.WebView
import com.peakmain.webview.callback.WebViewClientCallback
import com.peakmain.webview.fragment.WebViewFragment

/**
 * author ：Peakmain
 * createTime：2023/04/07
 * mail:2726449200@qq.com
 * describe：
 */
class ReplaceWebViewClient:WebViewClientCallback {
    override fun onPageStarted(view: WebView, url: String, fragment: WebViewFragment?) {

    }

    override fun onPageFinished(view: WebView, url: String, fragment: WebViewFragment?) {

    }

    override fun shouldOverrideUrlLoading(
        view: WebView,
        url: String,
        fragment: WebViewFragment?
    ): Boolean {
       return false
    }

    override fun onReceivedError(
        view: WebView?,
        err: Int,
        des: String?,
        url: String?,
        fragment: WebViewFragment?
    ) {
    }
}