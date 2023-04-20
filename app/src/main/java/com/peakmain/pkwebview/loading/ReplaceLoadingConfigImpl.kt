package com.peakmain.pkwebview.loading

import android.content.Context
import android.view.View
import com.peakmain.webview.interfaces.LoadingViewConfig
import com.peakmain.webview.view.AppProgressLoadingView

/**
 * author ：Peakmain
 * createTime：2023/04/20
 * mail:2726449200@qq.com
 * describe：
 */
class ReplaceLoadingConfigImpl : LoadingViewConfig {
    private lateinit var mAppProgressLoadingView: AppProgressLoadingView
    private var isShowLoading: Boolean = false
    override fun isShowLoading(): Boolean {
        return isShowLoading
    }

    override fun getLoadingView(context: Context): View {
        if (!::mAppProgressLoadingView.isInitialized) {
            mAppProgressLoadingView = AppProgressLoadingView(context)
        }
        isShowLoading = true
        return mAppProgressLoadingView

    }


    override fun hideLoading() {
        isShowLoading = false
        mAppProgressLoadingView.visibility = View.GONE
    }

    override fun showLoading(context: Context?) {
        if (!::mAppProgressLoadingView.isInitialized && context != null) {
            mAppProgressLoadingView = AppProgressLoadingView(context)
        }
        isShowLoading=true
        mAppProgressLoadingView.visibility = View.VISIBLE
    }
}