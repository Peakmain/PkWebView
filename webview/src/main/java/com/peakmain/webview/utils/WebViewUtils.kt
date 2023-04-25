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
}