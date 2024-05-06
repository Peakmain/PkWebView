package com.peakmain.webview.manager.cache

import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import com.peakmain.webview.bean.cache.CacheRequest
import com.peakmain.webview.utils.WebViewUtils
import com.peakmain.webview.view.PkWebView
import java.io.ByteArrayInputStream
import java.util.Locale

/**
 * author ：Peakmain
 * createTime：2024/3/4
 * mail:2726449200@qq.com
 * describe：
 */
class WebResourceResponseManager private constructor() {
    companion object {
        fun getResponse(
            context: Context?,
            request: WebResourceRequest,
            userAgent: String?,
            webView: PkWebView?,
        ): WebResourceResponse? {
            val url = request.url.toString()
            webView?.let {view->
                val webViewParams = view.getWebViewParams()
                webViewParams?.notCacheUrlArray?.forEach {
                    if (url.contains(it)) {
                        return null
                    }
                }
            }
            //资源类型
            val mimeType = WebViewUtils.instance.getMimeType(url)
            val cacheRequest = CacheRequest()
            cacheRequest.url = url
            cacheRequest.mimeType = mimeType
            cacheRequest.userAgent = userAgent
            val headers = request.requestHeaders
            cacheRequest.headers = headers
            return get(context, cacheRequest)
        }

        private fun get(context: Context?, request: CacheRequest): WebResourceResponse? {
            val response = RealCacheInterfaceCall(context, request).call() ?: return null
            //需要对获取到的response进行封装
            val responseHeaders = response.responseHeaders
            var contentType = ""
            var charset = ""
            var mineType = request.mimeType
            if (responseHeaders != null) {
                //contentType  "text/html; charset=utf-8"
                val contentTypeValue = getContentType(responseHeaders, "Content-Type")
                if (!TextUtils.isEmpty(contentTypeValue)) {
                    val typedArray = contentTypeValue!!.split(";").toTypedArray()
                    //[text/html, charset=utf-8]
                    if (typedArray.isNotEmpty()) {
                        contentType = typedArray[0]
                    }
                    if (typedArray.size >= 2) {
                        //charset=utf-8
                        charset = typedArray[1]
                        val charsetArray = charset.split("=").toTypedArray()
                        if (charsetArray.size >= 2) {
                            charset = charsetArray[1]
                        }
                    }
                }
            }
            if (!TextUtils.isEmpty(contentType)) {
                mineType = contentType
            }
            if (TextUtils.isEmpty(mineType)) {
                return null
            }
            val originBytes = response.originBytes
            if (originBytes == null || originBytes.isEmpty()) {
                return null
            }
            val bis = ByteArrayInputStream(originBytes)
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                val responseCode = response.responseCode
                var message = response.message
                if (TextUtils.isEmpty(message)) {
                    message = WebViewUtils.instance.getMessage(responseCode)
                }
                return WebResourceResponse(
                    mineType,
                    charset,
                    responseCode,
                    message,
                    response.responseHeaders,
                    bis
                )
            }
            return WebResourceResponse(mineType, charset, bis)
        }

        private fun getContentType(headers: Map<String, String>?, key: String): String? {
            if (headers == null) return null
            val value = headers[key]
            return value ?: headers[key.lowercase(Locale.getDefault())]
        }
    }

}