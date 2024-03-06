package com.peakmain.webview.manager.cache.implements

import android.util.LruCache
import com.peakmain.webview.bean.cache.WebResource
import com.peakmain.webview.manager.cache.MemorySizeCalculator
import com.peakmain.webview.manager.cache.interfaces.ICacheInterceptor
import com.peakmain.webview.utils.LogWebViewUtils

/**
 * author ：Peakmain
 * createTime：2024/3/4
 * mail:2726449200@qq.com
 * describe：内存缓存拦截器
 */
class MemoryCacheIntercept : ICacheInterceptor {
    private var mLruCache: LruCache<String, WebResource>? = null

    init {
        val size = MemorySizeCalculator.instance.getSize()
        if (size > 0) {
            mLruCache = ResourceMemoryCache(size)
        }
    }

    override fun cacheInterceptor(chain: ICacheInterceptor.Chain): WebResource? {
        val request = chain.request()


        mLruCache?.let {
            val resource = it.get(request.key)
            if (checkResourceValid(resource)) {
                LogWebViewUtils.e("内存缓存:${request.url}")
                return resource
            }
        }
        val resource = chain.process(request)
        //内存缓存资源
        if (mLruCache != null && checkResourceValid(resource) && resource?.isCacheable == true){
            LogWebViewUtils.e("内存缓存缓存数据:${request.url}")
            mLruCache?.put(request.key, resource)
        }
        return resource
    }

    private fun checkResourceValid(resource: WebResource?): Boolean {
        if (resource == null) return false
        return resource.originBytes != null && resource.originBytes.isNotEmpty()
                && resource.responseHeaders != null
                && resource.responseHeaders.isNotEmpty()
                && resource.isCacheByOurseleves
    }

    class ResourceMemoryCache constructor(maxSize: Int) : LruCache<String, WebResource>(maxSize) {
        override fun sizeOf(key: String?, value: WebResource?): Int {
            return value?.originBytes?.size ?: 0
        }
    }

}