package com.peakmain.webview.implement.loading

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ScaleDrawable
import android.util.TypedValue
import android.util.TypedValue.COMPLEX_UNIT_DIP
import android.view.Gravity
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
    private var mProgressBar: ProgressBar? = null
    private var mFrameLayout: FrameLayout? = null
    private var isShowLoading = false
    override fun isShowLoading(): Boolean {
        return isShowLoading
    }

    override fun getLoadingView(context: Context): View? {
        if (mFrameLayout == null) {
            mFrameLayout = FrameLayout(context)
            mFrameLayout?.apply {
                setBackgroundColor(Color.WHITE)
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        }
        if (mProgressBar == null) {
            mProgressBar = ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal)

            val height = TypedValue.applyDimension(
                COMPLEX_UNIT_DIP,
                5f,
                context.applicationContext.resources.displayMetrics
            ).toInt()
            mProgressBar?.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, height)

            // 创建一个从#FF5722到#CDDC39的水平渐变
            val colors = intArrayOf(Color.parseColor("#FF5722"), Color.parseColor("#CDDC39"))
            val gradientDrawable = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors)

            // 设置Drawable的形状和圆角半径
            gradientDrawable.shape = GradientDrawable.RECTANGLE
            gradientDrawable.cornerRadius = 8f

            // 创建一个ClipDrawable，将渐变Drawable作为其Drawable，并设置其绘制区域为进度条的宽度
            val clipDrawable =
                ClipDrawable(gradientDrawable, Gravity.START, ClipDrawable.HORIZONTAL)
            clipDrawable.level = 0

            // 创建一个ScaleDrawable，将ClipDrawable作为其Drawable，并设置其缩放比例为进度条的最大值
            val scaleDrawable = ScaleDrawable(clipDrawable, Gravity.START, 1f, -1f)
            scaleDrawable.level = 10000

            // 将ScaleDrawable设置为进度条的Drawable
            mProgressBar?.progressDrawable = scaleDrawable
            mFrameLayout?.addView(mProgressBar)
        }
        isShowLoading = true
        return mFrameLayout
    }

    override fun hideLoading() {
        isShowLoading = false
        mFrameLayout?.visibility = View.GONE
        mProgressBar?.progress = 0
    }

    override fun showLoading(context: Context?) {
        isShowLoading = true
        mFrameLayout?.visibility = View.VISIBLE
    }

    override fun setProgress(progress: Int) {
        mProgressBar?.progress = progress
    }

    override fun onDestroy() {
        mFrameLayout = null
        mProgressBar = null
    }
}