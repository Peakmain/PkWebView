package com.peakmain.webview.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.core.content.ContextCompat

/**
 * author ：Peakmain
 * createTime：2023/04/24
 * mail:2726449200@qq.com
 * describe：
 */
class WebViewUtils {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            WebViewUtils()
        }
    }

    @SuppressLint("MissingPermission")
    fun checkNetworkInfo(
        context: Context, noNetworkBlock: (() -> Unit)? = null,
        networkBlock: (() -> Unit)? = null
    ) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_NETWORK_STATE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (networkCapabilities == null ||
                    (!networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                            && !networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                ) {
                    noNetworkBlock?.invoke()
                } else {
                    networkBlock?.invoke()
                }
            } else {
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                if (activeNetworkInfo == null || !activeNetworkInfo.isConnected) {
                    noNetworkBlock?.invoke()
                }else{
                    networkBlock?.invoke()
                }
            }
        }

    }
    fun isImageType(url: String):Boolean{
        val lowerUrl = url.lowercase()
        return lowerUrl.endsWith(".png") || lowerUrl.endsWith(".jpg") || lowerUrl.endsWith(".jpeg")
                || lowerUrl.endsWith(".gif") || lowerUrl.endsWith(".webp") || lowerUrl.endsWith(".bmp")
                || lowerUrl.endsWith(".svg")
    }
    fun isCacheType(url: String): Boolean {
        val lowerUrl = url.lowercase()
        return isImageType(url)
                || lowerUrl.endsWith(".js") || lowerUrl.endsWith(".css")
                || lowerUrl.endsWith(".woff") || lowerUrl.endsWith(".woff2")
                || lowerUrl.endsWith(".ttf") || lowerUrl.endsWith(".otf") || lowerUrl.endsWith(".eot")
    }
    fun getMimeType(url: String): String {
        val lowerUrl = url.lowercase()
        return when {
            lowerUrl.endsWith(".js") -> "application/javascript"
            lowerUrl.endsWith(".css") -> "text/css"
            lowerUrl.endsWith(".png") -> "image/png"
            lowerUrl.endsWith(".jpg") || lowerUrl.endsWith(".jpeg") -> "image/jpeg"
            lowerUrl.endsWith(".gif") -> "image/gif"
            lowerUrl.endsWith(".webp") -> "image/webp"
            lowerUrl.endsWith(".bmp") -> "image/bmp"
            lowerUrl.endsWith(".svg") -> "image/svg+xml"
            lowerUrl.endsWith(".woff") -> "application/font-woff"
            lowerUrl.endsWith(".woff2") -> "font/woff2"
            lowerUrl.endsWith(".ttf") -> "application/x-font-ttf"
            lowerUrl.endsWith(".otf") -> "application/x-font-opentype"
            lowerUrl.endsWith(".eot") -> "application/vnd.ms-fontobject"
            // 可以根据需要添加其他文件类型的 MIME 类型
            else -> "text/plain"
        }
    }

}