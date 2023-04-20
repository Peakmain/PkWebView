package com.peakmain.webview.implement

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.FrameLayout.LayoutParams
import android.widget.ProgressBar
import com.peakmain.webview.interfaces.LoadingViewConfig

/**
 * author ：Peakmain
 * createTime：2023/04/20
 * mail:2726449200@qq.com
 * describe：
 */
class HorizontalProgressBarLoadingConfigImpl : LoadingViewConfig {
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mFrameLayout: FrameLayout
    private var isShowLoading = false
    override fun isShowLoading(): Boolean {
        return isShowLoading
    }

    override fun getLoadingView(context: Context): View? {
        if (!::mFrameLayout.isInitialized) {
            mFrameLayout = FrameLayout(context)
            mFrameLayout.setBackgroundColor(Color.WHITE)
            mFrameLayout.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        if (!::mProgressBar.isInitialized) {
            mProgressBar = ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal)
            mProgressBar.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 10)
            mFrameLayout.addView(mProgressBar)
        }
        isShowLoading = true
        return mFrameLayout
    }

    override fun hideLoading() {
        isShowLoading = false
        mFrameLayout.visibility = View.GONE
        mProgressBar.progress = 0
    }

    override fun showLoading(context: Context?) {
        isShowLoading = true
        mFrameLayout.visibility = View.VISIBLE
    }

    override fun setProgress(progress: Int) {
        mProgressBar.progress = progress
    }
}