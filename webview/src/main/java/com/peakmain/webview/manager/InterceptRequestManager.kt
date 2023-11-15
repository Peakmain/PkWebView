package com.peakmain.webview.manager

import android.graphics.Bitmap
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.concurrent.Callable
import java.util.concurrent.CancellationException
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

/**
 * author ：Peakmain
 * createTime：2023/11/15
 * mail:2726449200@qq.com
 * describe：
 */
class InterceptRequestManager private constructor() {
    private val mExecutorService: ExecutorService = Executors.newFixedThreadPool(5)
    private val mFutureMap =
        ConcurrentHashMap<String, Future<WebResourceResponse>>()

    companion object {
        @JvmStatic
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            InterceptRequestManager()
        }
    }

    fun loadImage(view: WebView, request: WebResourceRequest): WebResourceResponse? {
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
        return null
    }
}