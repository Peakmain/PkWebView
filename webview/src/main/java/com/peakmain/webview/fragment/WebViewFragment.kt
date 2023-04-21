package com.peakmain.webview.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.peakmain.webview.activity.WebViewActivity
import com.peakmain.webview.constants.WebViewConstants
import com.peakmain.webview.implement.loading.HorizontalProgressBarLoadingConfigImpl
import com.peakmain.webview.implement.loading.ProgressLoadingConfigImpl
import com.peakmain.webview.interfaces.LoadingViewConfig
import com.peakmain.webview.manager.WebViewManager
import com.peakmain.webview.manager.WebViewPool
import com.peakmain.webview.sealed.LoadingWebViewState
import com.peakmain.webview.view.PkWebView

/**
 * author ：Peakmain
 * createTime：2023/4/1
 * mail:2726449200@qq.com
 * describe：
 */
open class WebViewFragment : Fragment() {
    protected var mFileUploadCallbackFirst: ValueCallback<Uri>? = null
    protected var mFileUploadCallbackSecond: ValueCallback<Array<Uri>>? = null
    private var mWebView: PkWebView? = null
    private var mStartTime: Long = 0L
    private var mEndTime: Long = 0L
    private var mGroup: FrameLayout? = null
    private var mLoadingViewConfig: LoadingViewConfig? = null
    private var mLoadingView: View? = null
    private lateinit var mLoadingWebViewState: LoadingWebViewState
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        context?.let {
            val frameLayout = FrameLayout(it)
            frameLayout.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            initView(frameLayout)
            addLoadingView(frameLayout)
            return frameLayout
        }
        return null
    }

    private fun addLoadingView(frameLayout: FrameLayout) {
        mGroup = frameLayout
        val webViewParams = mWebView?.getWebViewParams() ?: return
        mLoadingWebViewState = webViewParams.mLoadingWebViewState
        if (mLoadingWebViewState == LoadingWebViewState.HorizontalProgressBarLoadingStyle) {
            webViewParams.mLoadingViewConfig = HorizontalProgressBarLoadingConfigImpl()
        } else if (webViewParams.mLoadingWebViewState == LoadingWebViewState.ProgressBarLoadingStyle) {
            webViewParams.mLoadingViewConfig = ProgressLoadingConfigImpl()
        }
        when (mLoadingWebViewState) {
            is LoadingWebViewState.NotLoading -> {
            }
            is LoadingWebViewState.ProgressBarLoadingStyle,
            LoadingWebViewState.CustomLoadingStyle,
            LoadingWebViewState.HorizontalProgressBarLoadingStyle -> {
                mLoadingViewConfig = webViewParams.mLoadingViewConfig
                mLoadingView = mLoadingViewConfig?.getLoadingView(frameLayout.context)
                mLoadingView?.visibility = View.VISIBLE
                if (mLoadingView?.parent != frameLayout) {
                    frameLayout.addView(mLoadingView, -1)
                }
            }
        }

    }


    private fun initView(fragmentView: FrameLayout?) {
        mWebView = WebViewPool.instance.getWebView()
        WebViewManager.instance.register(this)
        mWebView?.apply {
            //不显示滚动条
            isHorizontalScrollBarEnabled = false
            isVerticalScrollBarEnabled = false
            addWebView(fragmentView, this)
        }
        loadUrl2WebView(null)
    }

    private fun addWebView(fragmentView: FrameLayout?, pkWebView: PkWebView) {
        if (fragmentView != null && fragmentView.childCount <= 0) {
            fragmentView.addView(pkWebView)
        }
    }

    private fun loadUrl2WebView(oldUrl: String?) {
        var curUrl = oldUrl
        if (TextUtils.isEmpty(curUrl)) {
            curUrl = getWebViewUrl()
        }
        if (!TextUtils.isEmpty(curUrl)) {
            mWebView?.loadUrl(curUrl!!)
        } else {
            //LogUtils.e("WebView is empty page not found!")
        }
    }

    private fun getWebViewUrl(): String? {
        return arguments?.getString(WebViewConstants.LIBRARY_WEB_VIEW_URL)
    }


    fun canGoBack(): Boolean {
        return mWebView?.canGoBack() ?: false
    }

    fun webViewPageGoBack() {
        if (canGoBack()) {
            mWebView?.goBack()
        }
    }

    override fun onDestroy() {
        WebViewPool.instance.releaseWebView(mWebView)
        mWebView = null
        WebViewManager.instance.unRegister()
        super.onDestroy()
    }

    fun onPageStarted(view: WebView?, url: String?) {
        mStartTime = System.currentTimeMillis()
        if (mLoadingWebViewState != LoadingWebViewState.NotLoading
            && mLoadingViewConfig?.isShowLoading() == false
        ) {
            mLoadingViewConfig?.showLoading(view?.context)
        }
    }

    fun onPageFinished(view: WebView, url: String) {
        mEndTime = System.currentTimeMillis()
        Log.e("TAG", "消耗的时间:${(mEndTime - mStartTime) / 1000}")
        if (mLoadingWebViewState != LoadingWebViewState.NotLoading) {
            mLoadingViewConfig?.let {
                if (it.isShowLoading()) {
                    it.hideLoading()
                }
            }
        }
    }

    fun shouldOverrideUrlLoading(view: WebView, url: String) {


    }

    fun onReceivedError(view: WebView?, err: Int, des: String?, failingUrl: String?) {

    }

    fun onReceivedTitle(view: WebView?, title: String) {
        if (activity != null && activity is WebViewActivity) {
            val activity = activity as WebViewActivity
            activity.onReceivedTitle(title)
        }
    }

    fun onProgressChanged(view: WebView?, newProgress: Int) {
        if (mLoadingWebViewState ==
            LoadingWebViewState.HorizontalProgressBarLoadingStyle ||
            mLoadingWebViewState == LoadingWebViewState.CustomLoadingStyle
        ) {
            mLoadingViewConfig?.setProgress(newProgress)
        }
        if (mLoadingWebViewState != LoadingWebViewState.NotLoading) {
            mLoadingViewConfig?.let {
                if (newProgress == 100 && it.isShowLoading()) {
                    it.hideLoading()
                }
            }
        }

    }

    fun loadUrl(url: String) {
        mWebView?.loadUrl(url)
    }

    fun openFileInput(
        fileUploadCallbackFirst: ValueCallback<Uri>?,
        fileUploadCallbackSecond: ValueCallback<Array<Uri>>?,
        acceptType: String?
    ) {
        if (mFileUploadCallbackFirst != null) {
            mFileUploadCallbackFirst!!.onReceiveValue(null)
        }
        mFileUploadCallbackFirst = fileUploadCallbackFirst

        if (mFileUploadCallbackSecond != null) {
            mFileUploadCallbackSecond!!.onReceiveValue(null)
        }
        mFileUploadCallbackSecond = fileUploadCallbackSecond
        val i = Intent(Intent.ACTION_GET_CONTENT)
        i.addCategory(Intent.CATEGORY_OPENABLE)
        var type = acceptType
        if (TextUtils.isEmpty(acceptType)) {
            type = "image/*"
        }
        i.type = type
        activity?.startActivityForResult(
            Intent.createChooser(i, "File Chooser!"),
            REQUEST_CODE_FILE_PICKER
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode == REQUEST_CODE_FILE_PICKER) {
            if (resultCode == Activity.RESULT_OK) {
                if (intent != null) {
                    if (mFileUploadCallbackFirst != null) {
                        mFileUploadCallbackFirst!!.onReceiveValue(intent.data)
                        mFileUploadCallbackFirst = null
                    } else if (mFileUploadCallbackSecond != null) {
                        val dataUris: Array<Uri>? = try {
                            arrayOf(Uri.parse(intent.dataString))
                        } catch (e: Exception) {
                            null
                        }
                        mFileUploadCallbackSecond!!.onReceiveValue(dataUris)
                        mFileUploadCallbackSecond = null
                    }
                }
            } else {
                if (mFileUploadCallbackFirst != null) {
                    mFileUploadCallbackFirst!!.onReceiveValue(null)
                    mFileUploadCallbackFirst = null
                } else if (mFileUploadCallbackSecond != null) {
                    mFileUploadCallbackSecond!!.onReceiveValue(null)
                    mFileUploadCallbackSecond = null
                }
            }
        }
    }


    /**
     * 外链处理
     *
     * @param view view
     * @param url  url
     * @return boolean 不再需要被处理则返回true
     */
    protected fun onWebPageUrlLoading(view: WebView?, url: String?): Boolean {
        return false
    }


    companion object {
        private const val REQUEST_CODE_FILE_PICKER = 51426

    }

    override fun onResume() {
        super.onResume()
    }
}