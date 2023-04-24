package com.peakmain.webview.callback

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.peakmain.webview.fragment.WebViewFragment
import com.peakmain.webview.utils.LogWebViewUtils
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.concurrent.*
import java.util.jar.Manifest

/**
 * author ：Peakmain
 * createTime：2023/04/07
 * mail:2726449200@qq.com
 * describe：
 */
class DefaultWebViewClientCallback : WebViewClientCallback {
    private val mExecutorService: ExecutorService = Executors.newFixedThreadPool(5)
    private val mFutureMap =
        ConcurrentHashMap<String, Future<WebResourceResponse>>()

    override fun onPageFinished(view: WebView, url: String, fragment: WebViewFragment?) {
        LogWebViewUtils.e("再次來到onPageFinished")
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
        LogWebViewUtils.e("错误码：$err,错误信息:$des,错误url:$url")


    }

    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest
    ): WebResourceResponse? {
        if (view == null || !isImageType(request.url.toString())) return null
        val url = request.url.toString()
        var response: WebResourceResponse? = null
        if (mFutureMap.containsKey(url)) {
            return try {
                mFutureMap[url]?.get()
            } catch (e: Exception) {
                if (e !is CancellationException) {
                    e.printStackTrace()
                }
                mFutureMap.remove(url)
                null
            }
        }
        try {
            val future =
                mExecutorService.submit(Callable<WebResourceResponse> {
                    Glide.with(view.context)
                        .asBitmap()
                        .load(request.url.toString())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontTransform()
                        .submit()
                        .get()
                        .also { bitmap ->
                            val outStream = ByteArrayOutputStream()
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream)
                            val inputStream = ByteArrayInputStream(outStream.toByteArray())
                            response = WebResourceResponse("image/png", "UTF-8", inputStream)
                            response?.setStatusCodeAndReasonPhrase(200, "OK")
                            response?.responseHeaders = HashMap<String, String>()
                        }
                    response
                })
            mFutureMap.putIfAbsent(url, future)
            response = future.get()
            mFutureMap.remove(url)
        } catch (e: Exception) {
            e.printStackTrace()
            mFutureMap.remove(url)
        }
        return response
    }

    private fun isImageType(url: String): Boolean {
        return url.matches(Regex(".*\\.(png|jpe?g|gif|webp|bmp)$", RegexOption.IGNORE_CASE))
    }


    override fun onPageStarted(view: WebView, url: String, fragment: WebViewFragment?) {
        LogWebViewUtils.e("再次來到onPageStart")
    }

}