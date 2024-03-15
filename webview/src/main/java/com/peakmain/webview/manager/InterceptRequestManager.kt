package com.peakmain.webview.manager

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.webkit.WebResourceResponse
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.peakmain.webview.bean.cache.CacheRequest
import com.peakmain.webview.bean.cache.WebResource
import com.peakmain.webview.utils.LogWebViewUtils
import java.io.ByteArrayOutputStream

/**
 * author ：Peakmain
 * createTime：2023/11/15
 * mail:2726449200@qq.com
 * describe：
 */
class InterceptRequestManager private constructor() {
    private lateinit var mApplication: Application


    fun loadImage(context:Context,request: CacheRequest): WebResource? {
        val url = request.url
        try {
            // 使用 Glide 加载图片
            val bitmap = Glide.with(context)
                .asBitmap()
                .load(url)
                .listener(object :RequestListener<Bitmap>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        isFirstResource: Boolean,
                    ): Boolean {
                        LogWebViewUtils.e("图片加载失败:${e?.message}")
                        return false
                    }

                    override fun onResourceReady(
                        resource: Bitmap?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean,
                    ): Boolean { if (dataSource == DataSource.MEMORY_CACHE) {
                        // 图片来自内存缓存
                        // 处理内存缓存的逻辑
                        LogWebViewUtils.i("图片缓存来自内存缓存：${request.url}")
                    } else if (dataSource == DataSource.DATA_DISK_CACHE) {
                        // 图片来自磁盘缓存
                        // 处理磁盘缓存的逻辑
                        LogWebViewUtils.i("图片缓存来自磁盘缓存：${request.url}")
                    } else {
                        // 图片来自网络
                        // 处理网络加载的逻辑
                        LogWebViewUtils.i("图片缓存来自网络缓存：${request.url}")
                    }
                        return false
                    }

                })
                .submit()
                .get()

            // 创建 WebResource 对象并设置数据
            val remoteResource = WebResource()
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            remoteResource.originBytes = outputStream.toByteArray()
            remoteResource.responseCode = 200
            remoteResource.message = "OK"
            remoteResource.isModified = true // 如果需要根据实际情况设置是否修改了

            // 设置响应头
            val headersMap = HashMap<String, String>()
            // 添加你需要的响应头信息
            remoteResource.responseHeaders = headersMap

            return remoteResource
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    companion object {
        @JvmStatic
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            InterceptRequestManager()
        }
    }

    fun getLocalWebResourceResponse(fileName: String, mimeType: String): WebResourceResponse? {
        val inputStream = mApplication.assets.open(fileName)
        return WebResourceResponse(mimeType, "utf-8", inputStream)
    }

    fun init(application: Application) {
        this.mApplication = application
    }
}