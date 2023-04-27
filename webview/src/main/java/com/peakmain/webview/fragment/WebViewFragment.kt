package com.peakmain.webview.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient.*
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.peakmain.webview.bean.WebViewConfigBean
import com.peakmain.webview.constants.WebViewConstants
import com.peakmain.webview.implement.loading.HorizontalProgressBarLoadingConfigImpl
import com.peakmain.webview.implement.loading.ProgressLoadingConfigImpl
import com.peakmain.webview.interfaces.LoadingViewConfig
import com.peakmain.webview.manager.*
import com.peakmain.webview.sealed.HandleResult
import com.peakmain.webview.sealed.LoadingWebViewState
import com.peakmain.webview.utils.LogWebViewUtils
import com.peakmain.webview.utils.WebViewUtils
import com.peakmain.webview.view.PkWebView
import com.peakmain.webview.viewmodel.WebViewFragmentViewModel

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
    private val mH5UtilsParams = H5UtilsParams.instance
    private val mWebViewConfigBean by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            arguments?.getParcelable(
                WebViewConstants.LIBRARY_WEB_VIEW,
                WebViewConfigBean::class.java
            )
        else
            arguments?.getParcelable(WebViewConstants.LIBRARY_WEB_VIEW) as WebViewConfigBean?
    }
    private val mViewModel by viewModels<WebViewFragmentViewModel>()
    private var mWebViewHandle: WebViewHandle? = null

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
        mWebViewHandle = WebViewHandle(mWebView,webViewParams.mEventParamsKey)
        mLoadingWebViewState =
            mH5UtilsParams.mLoadingWebViewState ?: webViewParams.mLoadingWebViewState
        if (mLoadingWebViewState == LoadingWebViewState.HorizontalProgressBarLoadingStyle) {
            webViewParams.mLoadingViewConfig = HorizontalProgressBarLoadingConfigImpl()
        } else if (webViewParams.mLoadingWebViewState == LoadingWebViewState.ProgressBarLoadingStyle) {
            webViewParams.mLoadingViewConfig = ProgressLoadingConfigImpl()
        }
        mViewModel.addLoadingView(mLoadingWebViewState) {
            mLoadingViewConfig =
                mH5UtilsParams.mLoadingViewConfig ?: webViewParams.mLoadingViewConfig
            mLoadingView = mLoadingViewConfig?.getLoadingView(frameLayout.context)
            mLoadingView?.visibility = View.VISIBLE
            if (mLoadingView?.parent != frameLayout) {
                frameLayout.addView(mLoadingView, -1)
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
            mViewModel.addWebView(fragmentView, this)
        }
        loadUrl2WebView()
    }

    private fun loadUrl2WebView() {
        val curUrl = getWebViewUrl()
        if (!TextUtils.isEmpty(curUrl)) {
            mWebView?.loadUrl(curUrl!!)
        } else {
            //LogUtils.e("WebView is empty page not found!")
        }
    }

    private fun getWebViewUrl(): String? {
        return mWebViewConfigBean?.url
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
        mViewModel.onDestroy(mWebView)
        mWebView = null
        super.onDestroy()
    }

    fun onPageStarted(view: WebView?, url: String?) {
        mStartTime = System.currentTimeMillis()
        mViewModel.showLoading(view, mLoadingWebViewState, mLoadingViewConfig)
    }

    fun onPageFinished(view: WebView, url: String) {
        mEndTime = System.currentTimeMillis()
        LogWebViewUtils.e("消耗的时间:${(mEndTime - mStartTime) / 1000}")
        mViewModel.hideLoading(mLoadingWebViewState, mLoadingViewConfig)
    }

    fun shouldOverrideUrlLoading(view: WebView, url: String): HandleResult {
        mViewModel.shouldOverrideUrlLoading(activity, view, url)
        return mWebViewHandle?.handleUrl(url) ?: HandleResult.NotConsume
    }

    fun onReceivedError(view: WebView?, err: Int, des: String?, failingUrl: String?) {
        val context = context ?: return
        val webViewParams = mWebView?.getWebViewParams()
        WebViewUtils.instance.checkNetworkInfo(context.applicationContext, {
            //当前没有网
            LogWebViewUtils.e("当前没有网络")
            showNoNetwork(webViewParams, failingUrl)
        }) {
            when (err) {
                ERROR_HOST_LOOKUP, ERROR_CONNECT, ERROR_TIMEOUT, ERROR_IO ->
                    showNoNetwork(webViewParams, failingUrl)
            }
        }
    }

    private fun showNoNetwork(
        webViewParams: WebViewController.WebViewParams?,
        failingUrl: String?
    ) {
        mViewModel.showNoNetWorkView(webViewParams, activity, failingUrl, mWebView, mGroup) {
            mWebView?.reload()
        }
    }


    fun onReceivedTitle(view: WebView?, title: String) {
        mViewModel.onReceivedTitle(activity, view, title)
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
                if (!it.isShowLoading() && view?.context != null) {
                    it.showLoading(view.context)
                }
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


    companion object {
        private const val REQUEST_CODE_FILE_PICKER = 51426

    }

    override fun onResume() {
        super.onResume()
    }

    fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest) {

    }
}