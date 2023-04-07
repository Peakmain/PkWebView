package com.peakmain.webview.callback

import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebView
import com.peakmain.webview.fragment.WebViewFragment

/**
 * author ：Peakmain
 * createTime：2023/4/1
 * mail:2726449200@qq.com
 * describe：
 */
interface WebViewChromeClientCallback {
    fun onReceivedTitle(view: WebView?, title: String?, fragment: WebViewFragment?)
    fun openFileInput(
        fileUploadCallbackFirst: ValueCallback<Uri>?,
        fileUploadCallbackSecond: ValueCallback<Array<Uri>>?,
        acceptType: String?
    )
    fun onProgressChanged(view: WebView?, newProgress: Int, fragment: WebViewFragment?)
}