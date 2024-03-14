package com.peakmain.webview.callback

import android.net.Uri
import android.util.Log
import android.webkit.ValueCallback
import android.webkit.WebView
import com.peakmain.webview.fragment.WebViewFragment
import com.peakmain.webview.utils.LogWebViewUtils

/**
 * author ：Peakmain
 * createTime：2023/04/07
 * mail:2726449200@qq.com
 * describe：
 */
class DefaultWebViewChromeClientCallback:WebViewChromeClientCallback {
    override fun onReceivedTitle(view: WebView?, title: String?, fragment: WebViewFragment?) {
        LogWebViewUtils.e("收到标题:$title")
    }

    override fun openFileInput(
        fileUploadCallbackFirst: ValueCallback<Uri>?,
        fileUploadCallbackSecond: ValueCallback<Array<Uri>>?,
        acceptType: String?
    ) {
    }

    override fun onProgressChanged(view: WebView?, newProgress: Int, fragment: WebViewFragment?) {
       LogWebViewUtils.e("进度条发生变化：$newProgress")
    }
}