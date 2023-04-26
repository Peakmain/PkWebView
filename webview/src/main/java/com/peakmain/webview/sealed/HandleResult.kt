package com.peakmain.webview.sealed

/**
 * author ：Peakmain
 * createTime：2023/04/26
 * mail:2726449200@qq.com
 * describe：
 */
sealed class HandleResult {

    /**
     * 没有处理完成
     */
    object NotConsume : HandleResult()

    /**
     * 已经处理完成
     */
    object Consumed : HandleResult()

    /**
     * 正在处理中
     */
    object Consuming : HandleResult()
}