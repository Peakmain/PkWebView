package com.peakmain.webview.interfaces

import android.content.Context
import android.view.View

/**
 * author ：Peakmain
 * createTime：2023/04/20
 * mail:2726449200@qq.com
 * describe：
 */
interface LoadingViewConfig {

    fun isShowLoading(): Boolean
    fun getLoadingView(context: Context): View?
    fun hideLoading()
    fun showLoading(context: Context?)
    fun setProgress(progress: Int){

    }
}