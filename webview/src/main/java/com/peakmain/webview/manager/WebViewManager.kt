package com.peakmain.webview.manager

import android.webkit.WebView
import android.webkit.WebViewClient
import com.peakmain.webview.fragment.WebViewFragment
import com.peakmain.webview.view.PkWebView

/**
 * author ：Peakmain
 * createTime：2023/04/06
 * mail:2726449200@qq.com
 * describe：
 */
internal class WebViewManager private constructor(){
    companion object{
        val instance:WebViewManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            WebViewManager()
        }
    }
    private var mWebViewFragment: WebViewFragment? = null
    fun register(webViewFragment: WebViewFragment?){
        mWebViewFragment=webViewFragment
    }
    fun getFragment():WebViewFragment?{
        return mWebViewFragment
    }
    fun unRegister(){
        mWebViewFragment=null
    }
}