package com.peakmain.webview.manager.cache

/**
 * author ：Peakmain
 * createTime：2024/3/5
 * mail:2726449200@qq.com
 * describe：
 */
class MemorySizeCalculator private constructor() {
    private val MB = 1024 * 1024
    private val MB_15 = 15 * MB
    private val MB_10 = 10 * MB
    private val MB_5 = 5 * MB

    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            MemorySizeCalculator()
        }
    }

    fun getSize(): Int {
        val maxMemorySize = Runtime.getRuntime().maxMemory()
        val maxSizeByMB = (maxMemorySize / MB).toInt()

        if (maxSizeByMB >= 512) {
            return MB_15
        }

        if (maxSizeByMB >= 256) {
            return MB_10
        }

        return if (maxSizeByMB > 128) {
            MB_5
        } else 0
    }

}