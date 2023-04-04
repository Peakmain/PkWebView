package com.peakmain.webview

import androidx.annotation.ColorInt
import androidx.annotation.IntDef
import androidx.annotation.IntRange
import com.peakmain.webview.bean.WebViewConfigBean

/**
 * author ：Peakmain
 * createTime：2023/4/1
 * mail:2726449200@qq.com
 * describe：
 */
class WebViewConfig private constructor(){
    companion object  {
        const val MODE_LIGHT = 1
        const val MODE_DARK = 2
        @JvmStatic
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            WebViewConfig()
        }
    }

    private val webViewConfigBean = WebViewConfigBean()


    /**
     * MODE_LIGHT:白底黑字
     * MODE_DARK：黑底白字
     */
    @IntDef(
        MODE_LIGHT, MODE_DARK
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class StatusState


    /**
     * 设置url
     */
    fun url(url: String): WebViewConfig {
        webViewConfigBean.url = url
        return this
    }



}