package com.peakmain.webview.implement.loading

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
class ProgressLoadingConfigImpl : LoadingViewConfig {
    private lateinit var mAppProgressLoadingView: AppProgressLoadingView
    private var isShowLoading: Boolean = false
    override fun isShowLoading(): Boolean {
        return isShowLoading
    }

    override fun getLoadingView(context: Context): View {
        initProgressLoadingView(context)
        isShowLoading = true
        return mAppProgressLoadingView

    }


    override fun hideLoading() {
        isShowLoading = false
        mAppProgressLoadingView.visibility = View.GONE
    }

    override fun showLoading(context: Context?) {
        initProgressLoadingView(context)
        isShowLoading = true
        mAppProgressLoadingView.visibility = View.VISIBLE
    }

    private fun initProgressLoadingView(context: Context?) {
        if (!::mAppProgressLoadingView.isInitialized && context != null) {
            mAppProgressLoadingView = AppProgressLoadingView(context)
        }
    }
}