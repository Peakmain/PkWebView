package com.peakmain.webview.implement.init

import android.os.Build
import android.text.TextUtils
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import com.peakmain.webview.interfaces.InitWebViewSetting

/**
 * author ：Peakmain
 * createTime：2023/04/07
 * mail:2726449200@qq.com
 * describe：
 */
class DefaultInitWebViewSetting : InitWebViewSetting {
    override fun initWebViewSetting(webView: WebView, userAgent: String?) {
        val webSettings: WebSettings = webView.settings
        WebView.setWebContentsDebuggingEnabled(true)
        webSettings.apply {
            //允许网页JavaScript
            javaScriptEnabled = true
            allowFileAccess = true
            useWideViewPort = true
            // 支持混合内容（https和http共存）
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            // 开启DOM存储API
            domStorageEnabled = true
            // 开启数据库存储API
            databaseEnabled = true
            //不允许WebView中跳转到其他链接
            setSupportMultipleWindows(false)
            setSupportZoom(false)
            builtInZoomControls = false
            cacheMode = WebSettings.LOAD_DEFAULT
            allowFileAccess = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                safeBrowsingEnabled = true
            }
        }
        if (!TextUtils.isEmpty(userAgent)) {
            webSettings.userAgentString = userAgent
        }
        CookieManager.setAcceptFileSchemeCookies(true)
        CookieManager.getInstance().setAcceptCookie(true)
    }
}