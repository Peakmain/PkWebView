package com.peakmain.webview.manager.cache.implements

import android.content.Context
import android.webkit.WebResourceResponse
import com.peakmain.webview.bean.cache.WebResource
import com.peakmain.webview.manager.InterceptRequestManager
import com.peakmain.webview.manager.OKHttpManager
import com.peakmain.webview.manager.cache.interfaces.ICacheInterceptor
import com.peakmain.webview.utils.LogWebViewUtils
import com.peakmain.webview.utils.WebViewUtils

/**
 * author ：Peakmain
 * createTime：2024/3/4
 * mail:2726449200@qq.com
 * describe：网络缓存拦截器
 */
class NetworkCacheInterceptor(val context: Context?) : ICacheInterceptor {
    override fun cacheInterceptor(chain: ICacheInterceptor.Chain): WebResource? {
        val request = chain.request()

        val mimeType = request.mimeType
        val isCacheContentType = WebViewUtils.instance.isCacheContentType(mimeType)
        return context?.let {
            if (WebViewUtils.instance.isImageType(request.mimeType)) {
                InterceptRequestManager.instance.loadImage(context,request)
            } else{
                LogWebViewUtils.i("开始网络缓存:${request.url}")
                OKHttpManager(it).getResource(request,isCacheContentType)
            }

        }
    }
}