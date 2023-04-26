package com.peakmain.webview.annotation

import java.lang.annotation.RetentionPolicy


/**
 * author ：Peakmain
 * createTime：2023/04/26
 * mail:2726449200@qq.com
 * describe：
 */
@Target(AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
annotation class Handler(
    /**
     * 申明Scheme
     * @return String
     */
    val scheme: String,
    /**
     * 申明Authority
     * @return String
     */
    val authority: Array<String>
)