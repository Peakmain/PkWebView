package com.peakmain.webview.callback

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.peakmain.webview.fragment.WebViewFragment
import com.peakmain.webview.manager.InterceptRequestManager
import com.peakmain.webview.utils.LogWebViewUtils
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.concurrent.*

/**
 * author ：Peakmain
 * createTime：2023/04/07
 * mail:2726449200@qq.com
 * describe：
 */
class DefaultWebViewClientCallback : WebViewClientCallback {


    override fun onPageFinished(view: WebView, url: String, fragment: WebViewFragment?) {
        LogWebViewUtils.e("再次來到onPageFinished")
    }

    override fun shouldOverrideUrlLoading(
        view: WebView,
        url: String,
        fragment: WebViewFragment?,
    ): Boolean? {
        val activity = fragment?.activity
        //处理电话功能
        if (url.startsWith("tel")) {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                activity?.startActivity(intent)
                return true
            } catch (ex: ActivityNotFoundException) {
                ex.printStackTrace()
            }
        }
        if (url.startsWith("mailto:")) {
            if (activity != null) {
                val emailUri = Uri.parse(url)
                val emailIntent = Intent(Intent.ACTION_SENDTO, emailUri)
                activity.startActivity(emailIntent)
                return true
            }
            return true
        }
        // 对支付宝和微信的支付页面点击做特殊处理
        if (url.contains("alipays://platformapi") || url.contains("weixin://wap/pay?")) {
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
        fragment: WebViewFragment?,
    ) {
        LogWebViewUtils.e("错误码：$err,错误信息:$des,错误url:$url")


    }

    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest,
    ): WebResourceResponse? {
        return view?.run {
            if (isImageType(request.url.toString())) {
                InterceptRequestManager.instance.loadImage(this, request)
            } else {
                null
            }
        }

    }

    private fun isImageType(url: String): Boolean {
        return url.matches(Regex(".*\\.(png|jpe?g|gif|webp|bmp)$", RegexOption.IGNORE_CASE))
    }


    override fun onPageStarted(view: WebView, url: String, fragment: WebViewFragment?) {
        LogWebViewUtils.e("再次來到onPageStart")
    }

}