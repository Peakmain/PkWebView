package com.peakmain.webview.manager.cache

import android.content.Context
import com.peakmain.webview.utils.WebViewUtils
import java.io.File

/**
 * author ：Peakmain
 * createTime：2024/3/5
 * mail:2726449200@qq.com
 * describe：
 */
class CacheConfig private constructor() {

    // 缓存路径
    private var mCacheDir: String? = null

    // 应用版本号
    private var mVersion = 0
    private var mDiskCacheSize: Long = 0
    private var mMemCacheSize = 0
    fun getCacheDir(): String? {
        return mCacheDir
    }

    fun getVersion(): Int {
        return mVersion
    }

    fun setVersion(version: Int) {
        mVersion = version
    }

    fun getDiskCacheSize(): Long {
        return mDiskCacheSize
    }

    fun getMemCacheSize(): Int {
        return mMemCacheSize
    }

    class Builder(context: Context) {
        private var cacheDir: String = context.cacheDir.toString() + File.separator + "cache"
        private var version: Int
        private var diskCacheSize = DEFAULT_DISK_CACHE_SIZE.toLong()
        fun setCacheDir(cacheDir: String): Builder {
            this.cacheDir = cacheDir
            return this
        }

        fun setVersion(version: Int): Builder {
            this.version = version
            return this
        }

        fun setDiskCacheSize(diskCacheSize: Long): Builder {
            this.diskCacheSize = diskCacheSize
            return this
        }


        fun build(): CacheConfig {
            val config = CacheConfig()
            config.mCacheDir = cacheDir
            config.mVersion = version
            config.mDiskCacheSize = diskCacheSize
            return config
        }

        companion object {
            private const val CACHE_DIR_NAME = "cache_webview"
            private const val DEFAULT_DISK_CACHE_SIZE = 100 * 1024 * 1024
        }

        init {
            cacheDir = context.cacheDir.toString() + File.separator + CACHE_DIR_NAME
            version = WebViewUtils.instance.getVersionCode(context).toInt()
        }
    }
}