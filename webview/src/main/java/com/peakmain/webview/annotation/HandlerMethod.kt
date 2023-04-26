package com.peakmain.webview.annotation

/**
 * author ：Peakmain
 * createTime：2023/4/26
 * mail:2726449200@qq.com
 * describe：
 */
@Target(
    AnnotationTarget.FUNCTION
)
@Retention(AnnotationRetention.RUNTIME)
annotation class HandlerMethod(
    val path: String
)