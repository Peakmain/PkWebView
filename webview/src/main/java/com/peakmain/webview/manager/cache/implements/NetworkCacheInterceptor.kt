package com.peakmain.webview.manager.cache.implements

import android.webkit.WebResourceResponse
import com.peakmain.webview.manager.cache.interfaces.ICacheInterceptor

/**
 * author ：Peakmain
 * createTime：2024/3/4
 * mail:2726449200@qq.com
 * describe：网络缓存拦截器
 */
class NetworkCacheInterceptor :ICacheInterceptor {
    override fun cacheInterceptor(chain: ICacheInterceptor.Chain): WebResourceResponse {
        // TODO:
    }
}