package com.peakmain.webview.utils

import android.util.Log
import com.peakmain.webview.BuildConfig

/**
 * author ：Peakmain
 * createTime：2023/04/24
 * mail:2726449200@qq.com
 * describe：
 */
object LogWebViewUtils {
    private const val TAG = "PKWebView"
    @JvmStatic
    fun e(message: String) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, message)
        }
    }
}