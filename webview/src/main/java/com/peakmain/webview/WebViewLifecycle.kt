package com.peakmain.webview

import android.os.Looper
import android.webkit.WebView
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.lifecycle.*


/**
 * author ：Peakmain
 * createTime：2023/4/1
 * mail:2726449200@qq.com
 * describe：
 */
class WebViewLifecycle(var webView: WebView? = null) : LifecycleEventObserver {
    private fun onResume() {
        webView?.onResume()
    }

    private fun onPause() {
        webView?.onPause()
    }

    private fun onDestroy() {
        if (Looper.getMainLooper() != Looper.myLooper()) return
        webView?.apply {
            stopLoading()
            handler?.removeCallbacksAndMessages(null)
            removeAllViews()
            val mViewGroup = parent as ViewGroup?
            mViewGroup?.removeView(this)
            webChromeClient = null
            webViewClient = WebViewClient()
            tag = null
            clearHistory()
            destroy()
        }
        webView = null
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when(event){
            Lifecycle.Event.ON_RESUME-> onResume()
            Lifecycle.Event.ON_PAUSE->onPause()
            Lifecycle.Event.ON_DESTROY-> onDestroy()
            else -> {
            }
        }
    }
}