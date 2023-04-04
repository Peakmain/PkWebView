package com.peakmain.webview.view

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView
import com.peakmain.webview.R
import com.peakmain.webview.helper.WebViewHelper

/**
 * author ：Peakmain
 * createTime：2023/4/1
 * mail:2726449200@qq.com
 * describe：
 */
class PkWebView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = R.style.PkWebView
) :
    WebView(context, attrs, defStyle) {

    init {
        WebViewHelper.loadWebViewResource(context)
    }

    var mLoadUrlListener: ((String) -> String)? = null

    override fun loadUrl(url: String) {
        var newUrl = url
        if (mLoadUrlListener != null) {
            newUrl = mLoadUrlListener!!.invoke(url)
        }
        super.loadUrl(newUrl)
    }

    override fun loadUrl(url: String, additionalHttpHeaders: MutableMap<String, String>) {
        mLoadUrlListener?.invoke(url)
        super.loadUrl(url, additionalHttpHeaders)
    }


}