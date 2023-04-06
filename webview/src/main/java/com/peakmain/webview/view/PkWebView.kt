package com.peakmain.webview.view

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView
import com.peakmain.webview.helper.WebViewHelper
import com.peakmain.webview.manager.WebViewController

/**
 * author ：Peakmain
 * createTime：2023/4/1
 * mail:2726449200@qq.com
 * describe：
 */
class PkWebView :WebView {

    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
        WebViewHelper.loadWebViewResource(context)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        WebViewHelper.loadWebViewResource(context)
    }
    constructor(
        context: Context
    ) : this(context,null)


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