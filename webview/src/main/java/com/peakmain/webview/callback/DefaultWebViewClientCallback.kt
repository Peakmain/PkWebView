package com.peakmain.webview.callback

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import android.util.Log
import android.webkit.*
import androidx.core.content.ContextCompat.startActivity
import com.peakmain.webview.callback.WebViewClientCallback
import com.peakmain.webview.fragment.WebViewFragment

/**
 * author ：Peakmain
 * createTime：2023/04/07
 * mail:2726449200@qq.com
 * describe：
 */
class DefaultWebViewClientCallback : WebViewClientCallback {
    override fun onPageFinished(view: WebView, url: String, fragment: WebViewFragment?) {
        Log.e("TAG", "再次來到onPageFinished")
    }

    override fun shouldOverrideUrlLoading(
        view: WebView,
        url: String,
        fragment: WebViewFragment?
    ): Boolean? {
        val activity = fragment?.activity
        //处理电话功能
        if (url.startsWith("tel")) {
            try {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse(url))
                activity?.startActivity(intent)
                return true
            } catch (ex: ActivityNotFoundException) {
                ex.printStackTrace()
            }
        } else if (url.startsWith("mailto:")) {
            if (activity != null) {
                val i = Intent(Intent.ACTION_SEND)
                i.setType("plain/text").putExtra(Intent.EXTRA_EMAIL, arrayOf(url))
                activity.startActivity(i)
                return true
            }
            return true
        }
        // 对支付宝和微信的支付页面点击做特殊处理
        else if (url.contains("alipays://platformapi") || url.contains("weixin://wap/pay?")) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            activity?.startActivity(intent)
            return true
        }
        //处理外部链接
        return null
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