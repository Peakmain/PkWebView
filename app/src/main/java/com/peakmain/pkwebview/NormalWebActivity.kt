package com.peakmain.pkwebview

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.peakmain.webview.utils.LogWebViewUtils


/**
 * author ：Peakmain
 * createTime：2024/3/20
 * mail:2726449200@qq.com
 * describe：
 */
class NormalWebActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    private var targetUrl: String = ""
    private lateinit var flGroup: FrameLayout
    var mStartTime = System.currentTimeMillis()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        targetUrl = intent.getStringExtra("url").toString()
        initView()
        initWebSettings()
        initWebCacheAndLoad()
    }

    private fun initView() {
        flGroup = findViewById(R.id.fl)
        webView = WebView(this)
        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        flGroup.addView(webView, params)
    }

    private fun initWebSettings() {
        // 基础配置
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.javaScriptEnabled = true
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        webSettings.useWideViewPort = true
        webSettings.domStorageEnabled = true
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
    }


    private fun initWebCacheAndLoad() {
        webView.webViewClient = mWebViewClient
        webView.loadUrl(targetUrl)
    }


    private val mWebViewClient: WebViewClient = object : WebViewClient() {

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            LogWebViewUtils.e("普通WebView 的消耗时间:${System.currentTimeMillis() - mStartTime}")

        }

        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
            // 接受所有网站的证书
            handler.proceed()
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }
    }

    override fun onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack()
        } else {
            finish()
        }
    }

    override fun onDestroy() {
        if (flGroup != null) {
            flGroup.removeView(webView)
        }
        if (webView != null) {
            webView.clearHistory()
            webView.settings.javaScriptEnabled = false
            webView.settings.blockNetworkImage = false
            webView.clearCache(true)
            webView.destroy()
        }
        super.onDestroy()
    }


}