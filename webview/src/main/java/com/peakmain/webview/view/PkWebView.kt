package com.peakmain.webview.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.text.TextUtils
import android.util.AttributeSet
import android.webkit.WebView
import com.peakmain.webview.manager.WebViewController

/**
 * author ：Peakmain
 * createTime：2023/4/1
 * mail:2726449200@qq.com
 * describe：
 */
class PkWebView : WebView {
    var blackMonitorCallback: ((Boolean) -> Unit)? = null


    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {

    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    constructor(
        context: Context,
    ) : this(context, null)



    fun postBlankMonitorRunnable() {
        removeCallbacks(blackMonitorRunnable)
        postDelayed(blackMonitorRunnable, 1000)
    }

    fun removeBlankMonitorRunnable() {
        removeCallbacks(blackMonitorRunnable)
    }

    private val blackMonitorRunnable = Runnable {
        val height = measuredHeight / 6
        val width = measuredWidth / 6
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        Canvas(bitmap).run {
            draw(this)
        }
        with(bitmap) {
            //像素总数
            val pixelCount = (width * height).toFloat()
            val whitePixelCount = getWhitePixelCount()
            recycle()
            if (whitePixelCount == 0) return@Runnable
            post {
                blackMonitorCallback?.invoke(whitePixelCount / pixelCount > 0.95)
            }
        }

    }

    private fun Bitmap.getWhitePixelCount(): Int {
        var whitePixelCount = 0
        for (i in 0 until width) {
            for (j in 0 until height) {
                if (getPixel(i, j) == -1) {
                    whitePixelCount++
                }
            }
        }
        return whitePixelCount
    }

    private var mParams: WebViewController.WebViewParams? = null
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

    fun setWebViewParams(params: WebViewController.WebViewParams) {
        this.mParams = params
    }

    fun getWebViewParams(): WebViewController.WebViewParams? {
        return mParams
    }

    /**
     * 释放webView
     */
    fun release() {

    }

    /**
     * 预加载
     */
    fun preLoadUrl( url: String) {
        if (!TextUtils.isEmpty(url)) {
            loadUrl(url)
        }
    }


}