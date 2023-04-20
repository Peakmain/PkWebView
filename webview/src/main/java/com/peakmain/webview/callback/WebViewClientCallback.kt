package com.peakmain.webview.callback

import android.webkit.WebView
import com.peakmain.webview.fragment.WebViewFragment

/**
 * author ：Peakmain
 * createTime：2023/4/1
 * mail:2726449200@qq.com
 * describe：
 */
interface WebViewClientCallback {
    fun onPageStarted(view: WebView, url: String,fragment: WebViewFragment?)
    fun onPageFinished(view: WebView, url: String, fragment: WebViewFragment?)
    fun shouldOverrideUrlLoading(view: WebView, url: String,fragment: WebViewFragment?): Boolean?
    fun onReceivedError(view: WebView?, err: Int, des: String?, url: String?,fragment: WebViewFragment?)
}