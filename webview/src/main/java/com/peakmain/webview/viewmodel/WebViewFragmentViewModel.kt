package com.peakmain.webview.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel
import com.peakmain.webview.R
import com.peakmain.webview.activity.WebViewActivity
import com.peakmain.webview.interfaces.LoadingViewConfig
import com.peakmain.webview.manager.WebViewController
import com.peakmain.webview.manager.WebViewManager
import com.peakmain.webview.manager.WebViewPool
import com.peakmain.webview.sealed.LoadingWebViewState
import com.peakmain.webview.view.PkWebView

/**
 * author ：Peakmain
 * createTime：2023/04/25
 * mail:2726449200@qq.com
 * describe：
 */
class WebViewFragmentViewModel() : ViewModel() {
    fun addWebView(fragmentView: FrameLayout?, pkWebView: PkWebView) {
        if (fragmentView != null && fragmentView.childCount <= 0) {
            fragmentView.addView(pkWebView)
        }
    }

    fun onDestroy(view: PkWebView?) {
        WebViewPool.instance.releaseWebView(view)
        WebViewManager.instance.unRegister()
    }

    fun hideStatusView(statusView: View?, activity: Activity?) {
        if (statusView == null || activity == null) return
        if (statusView.visibility == View.VISIBLE) {
            statusView.visibility = View.INVISIBLE
        }
        val webViewActivity = activity as WebViewActivity
        webViewActivity.isNotifyTitle(true)
    }

    fun onReceivedTitle(activity: Activity?, view: WebView?, title: String) {
        if (!isWebViewActivity(activity)) return
        val webViewActivity = activity as WebViewActivity
        webViewActivity.onReceivedTitle(title)
    }

    fun shouldOverrideUrlLoading(activity: Activity?, view: WebView, url: String) {
        if (!isWebViewActivity(activity)) return
        val webViewActivity = activity as WebViewActivity
        webViewActivity.shouldOverrideUrlLoading(view, url)
    }

    private fun isWebViewActivity(activity: Activity?): Boolean {
        return activity != null && activity is WebViewActivity
    }

    fun hideLoading(
        loadingWebViewState: LoadingWebViewState,
        loadingViewConfig: LoadingViewConfig?
    ) {
        if (loadingWebViewState != LoadingWebViewState.NotLoading) {
            loadingViewConfig?.let {
                if (it.isShowLoading()) {
                    it.hideLoading()
                }
            }
        }
    }

    fun showLoading(
        view: WebView?,
        loadingWebViewState: LoadingWebViewState,
        loadingViewConfig: LoadingViewConfig?
    ) {
        if (loadingWebViewState != LoadingWebViewState.NotLoading
            && loadingViewConfig?.isShowLoading() == false
        ) {
            loadingViewConfig.showLoading(view?.context)
        }
    }

    fun addLoadingView(loadingWebViewState: LoadingWebViewState, block: (() -> Unit)? = null) {
        when (loadingWebViewState) {
            is LoadingWebViewState.NotLoading -> {
            }
            is LoadingWebViewState.ProgressBarLoadingStyle,
            LoadingWebViewState.CustomLoadingStyle,
            LoadingWebViewState.HorizontalProgressBarLoadingStyle -> {
                block?.invoke()
            }
        }

    }

    fun showNoNetWorkView(
        webViewParams: WebViewController.WebViewParams?,
        activity: Activity?,
        failingUrl: String?,
        webView: PkWebView?,
        frameLayout: FrameLayout?,
        retryClickListener: ((String?) -> Unit)? = null
    ) {
        if (webViewParams == null)
            showNoNetworkViewById(
                activity,
                failingUrl,
                R.layout.webview_no_network,
                frameLayout,
                null,
                retryClickListener
            )
        else
            showNoNetworkViewByParams(
                activity,
                failingUrl,
                webView,
                webViewParams,
                frameLayout,
                retryClickListener
            )
    }

    private fun showNoNetworkViewByParams(
        activity: Activity?,
        failingUrl: String?,
        webView: PkWebView?,
        webViewParams: WebViewController.WebViewParams,
        frameLayout: FrameLayout?,
        retryClickListener: ((String?) -> Unit)? = null
    ) {
        var noNetWorkView = webViewParams.mNoNetWorkView
        val noNetWorkViewId = webViewParams.mNoNetWorkViewId
        val netWorkViewBlock = webViewParams.mNetWorkViewBlock
        if (noNetWorkView == null && noNetWorkViewId != 0) {
            noNetWorkView = showNoNetworkViewById(
                activity,
                failingUrl,
                noNetWorkViewId,
                frameLayout,
                null,
                retryClickListener

            )
            netWorkViewBlock?.invoke(noNetWorkView, webView, failingUrl)
        } else if (noNetWorkView != null && noNetWorkViewId == 0) {
            showNoNetworkViewById(
                activity,
                failingUrl,
                0,
                frameLayout,
                noNetWorkView,
                retryClickListener
            )
        }
    }

    private fun showNoNetworkViewById(
        activity: Activity?,
        failingUrl: String?,
        @LayoutRes layoutId: Int,
        frameLayout: FrameLayout?,
        noNetWorkView: View?,
        retryClickListener: ((String?) -> Unit)? = null
    ): View? {
        var mNoNetworkView = noNetWorkView
        if (mNoNetworkView == null && layoutId != 0) {
            mNoNetworkView = View.inflate(activity, layoutId, null)
        }
        addStatusView(mNoNetworkView, frameLayout)
        mNoNetworkView?.setOnClickListener {
            hideStatusView(mNoNetworkView, activity)
            retryClickListener?.invoke(failingUrl)
        }
        mNoNetworkView?.visibility = View.VISIBLE
        if (activity == null) return mNoNetworkView
        (activity as WebViewActivity).isNotifyTitle(false)
        return mNoNetworkView
    }

    private fun addStatusView(statusView: View?, frameLayout: FrameLayout?) {
        if (statusView == null) return
        if (statusView.parent != frameLayout) {
            statusView.layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            frameLayout?.addView(statusView)
        }

    }

}