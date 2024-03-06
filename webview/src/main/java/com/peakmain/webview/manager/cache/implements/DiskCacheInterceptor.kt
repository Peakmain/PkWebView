package com.peakmain.webview.manager.cache.implements

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.peakmain.webview.bean.cache.WebResource
import com.peakmain.webview.manager.cache.CacheConfig
import com.peakmain.webview.manager.cache.interfaces.ICacheInterceptor
import com.peakmain.webview.utils.LogWebViewUtils
import com.peakmain.webview.utils.WebViewUtils
import com.peakmain.webview.utils.disk.DiskLruCache
import okhttp3.Headers
import okio.buffer
import okio.sink
import okio.source
import java.io.File
import java.util.Locale
import kotlin.math.sin

/**
 * author ：Peakmain
 * createTime：2024/3/4
 * mail:2726449200@qq.com
 * describe：磁盘缓存拦截器
 */
class DiskCacheInterceptor(val context: Context?) : ICacheInterceptor {
    private var mDiskLruCache: DiskLruCache? = null


    override fun cacheInterceptor(chain: ICacheInterceptor.Chain): WebResource? {
        val request = chain.request()
        createLruCache()
        var webResource = getWebResourceFromDiskCache(request.key)
        if (webResource != null && isContentTypeCacheable(webResource)) {
            LogWebViewUtils.e("磁盘缓存：${request.url}")
            return webResource
        }
        webResource = chain.process(request)
        //磁盘进行缓存
        if (webResource != null && isContentTypeCacheable(webResource)) {
            LogWebViewUtils.e("磁盘缓存缓存数据:${request.url}")
            cacheToDisk(request.key, webResource)
        }
        return webResource
    }

    private fun createLruCache() {
        if (mDiskLruCache != null && !mDiskLruCache!!.isClosed) {
            return
        }
        val build = CacheConfig.Builder(context!!).build()
        val dir = build.getCacheDir()
        val version = build.getVersion()
        val cacheSize = build.getDiskCacheSize()

        try {
            mDiskLruCache = DiskLruCache.open(File(dir), version, 2, cacheSize)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun cacheToDisk(key: String?, webResource: WebResource) {
        if (!webResource.isCacheable || mDiskLruCache == null || mDiskLruCache!!.isClosed) return
        webResource.isCacheByOurseleves = true
        val edit = mDiskLruCache?.edit(key) ?: return
        val outputStream = edit.newOutputStream(0)
        var sink = outputStream.sink().buffer()
        sink.writeUtf8(webResource.responseCode.toString()).writeByte('\n'.code)
        sink.writeUtf8(webResource.message).writeByte('\n'.code)
        val headers = webResource.responseHeaders
        sink.writeDecimalLong(headers.size.toLong()).writeByte('\n'.code)
        for ((headerKey, value) in headers) {
            sink.writeUtf8(headerKey)
                .writeUtf8(": ")
                .writeUtf8(value)
                .writeByte('\n'.code)
        }
        sink.flush()
        sink.close()
        //写入body
        val bodyOutputSteam = edit.newOutputStream(1)
        sink = bodyOutputSteam.sink().buffer()
        val originBytes = webResource.originBytes
        if (originBytes != null && originBytes.isNotEmpty()) {
            sink.write(originBytes)
            sink.flush()
            edit.commit()
        }
        sink.close()
    }

    private fun isContentTypeCacheable(resource: WebResource?): Boolean {
        if (resource == null) {
            return false
        }
        val contentType: String? = WebViewUtils.instance.getContentType(resource)
        return contentType != null && WebViewUtils.instance.isCacheContentType(contentType)
    }



    private fun getWebResourceFromDiskCache(key: String?): WebResource? {
        if (mDiskLruCache?.isClosed == true) return null
        val snapshot = mDiskLruCache?.get(key)
        snapshot?.let {
            //获取状态
            val source = snapshot.getInputStream(0).source().buffer()
            val responseCode = source.readUtf8LineStrict()//读取一行数据，直到遇到换行符为止
            val message = source.readUtf8LineStrict()
            //获取headers
            var headerSize = source.readDecimalLong()
            val headers: Map<String, String>?
            val builder = Headers.Builder()
            val placeHolder = source.readUtf8LineStrict()
            if (!TextUtils.isEmpty(placeHolder.trim())) {
                builder.add(placeHolder)
                headerSize--
            }
            for (i in 0 until headerSize) {
                val line = source.readUtf8LineStrict()
                if (!TextUtils.isEmpty(line)) {
                    builder.add(line)
                }
            }
            headers = WebViewUtils.instance.generateHeadersMap(builder.build())

            //获取到body
            val inputStream = snapshot.getInputStream(1)
            if (inputStream != null) {
                val webResource = WebResource()
                webResource.message = message
                webResource.responseCode = Integer.parseInt(responseCode)
                webResource.originBytes = WebViewUtils.instance.streamToBytes(inputStream)
                webResource.isModified = false
                webResource.responseHeaders = headers
                return webResource
            }
            snapshot.close()
        }
        return null
    }
}