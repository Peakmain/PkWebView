package com.peakmain.webview.implement

import android.net.http.SslError
import android.util.Log
import android.webkit.*
import com.peakmain.webview.callback.WebViewClientCallback
import com.peakmain.webview.fragment.WebViewFragment

/**
 * author ：Peakmain
 * createTime：2023/04/07
 * mail:2726449200@qq.com
 * describe：
 */
class DefaultWebViewClientCallback:WebViewClientCallback {
    override fun onPageFinished(view: WebView, url: String, fragment: WebViewFragment?) {
        Log.e("TAG", "再次來到onPageFinished")
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


    override fun onPageStarted(view: WebView, url: String, fragment: WebViewFragment?) {
        Log.e("TAG", "再次來到onPageStart")
    }

}