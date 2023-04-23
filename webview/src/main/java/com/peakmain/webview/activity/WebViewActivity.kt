package com.peakmain.webview.activity

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import com.peakmain.webview.R
import com.peakmain.webview.bean.WebViewConfigBean
import com.peakmain.webview.constants.WebViewConstants
import com.peakmain.webview.fragment.WebViewFragment
import com.peakmain.webview.manager.H5UtilsParams
import com.peakmain.webview.sealed.StatusBarState
import com.peakmain.webview.viewmodel.WebViewModel

/**
 * author ：Peakmain
 * createTime：2023/4/1
 * mail:2726449200@qq.com
 * describe：
 */
class WebViewActivity : BaseWebViewActivity() {

    override fun getLayoutId(): Int = R.layout.layout_activity_web_view
    private val mWebViewConfigBean by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            intent.extras?.getParcelable(
                WebViewConstants.LIBRARY_WEB_VIEW,
                WebViewConfigBean::class.java
            )
        else
            intent.extras?.getParcelable(WebViewConstants.LIBRARY_WEB_VIEW) as WebViewConfigBean?
    }
    var mWebViewFragment: WebViewFragment? = null
    lateinit var mWebViewModel: WebViewModel
    private val mH5UtilsParams = H5UtilsParams.instance
    override fun initView() {
        if (!::mWebViewModel.isInitialized) {
            mWebViewModel = WebViewModel()
        }
        mWebViewToolbar?.get()?.visibility =
            if (mH5UtilsParams.isShowToolBar) View.VISIBLE else View.GONE
        initFragment()
        initListener()
    }

    private fun initListener() {
        mIvBack?.get()?.setOnClickListener {
            exit()
        }
    }

    private fun exit() {
        if (!canGoBack()) {
            finish()
            return
        }
        webViewGoBack()
    }

    private fun initFragment() {
        val bundle = Bundle()
        bundle.putParcelable(WebViewConstants.LIBRARY_WEB_VIEW, mWebViewConfigBean)
        mWebViewFragment = WebViewFragment()
        mWebViewFragment!!.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.root, mWebViewFragment!!)
            .commitAllowingStateLoss()
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        /*   val url = intent.getEx(WebViewConstants.LIBRARY_WEB_VIEW)
           if (mWebViewFragment != null && !TextUtils.isEmpty(url)) {
               mWebViewFragment!!.loadUrl(intent.getStringExtra(WebViewConstants.LIBRARY_WEB_VIEW_URL)!!)
           }*/
    }

    private fun canGoBack(): Boolean {
        return mWebViewFragment != null && mWebViewFragment!!.canGoBack()
    }

    private fun webViewGoBack() {
        mWebViewFragment?.webViewPageGoBack()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (canGoBack()) {
                webViewGoBack()
                true
            } else {
                finish()
                true
            }
        } else super.onKeyDown(keyCode, event)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mWebViewFragment?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        clear()
    }


    fun onReceivedTitle(title: String) {
        mTvTitle?.get()?.text = title
        mH5UtilsParams.apply {
            updateStatusBar?.invoke(title, this@WebViewActivity)
            updateToolBarBar?.invoke(title, this@WebViewActivity)
        }
    }

    fun updateStateBar(
        statusBarState: StatusBarState = StatusBarState.LightModeState,
        statusBarColor: Int = Color.WHITE
    ) {
        mWebViewConfigBean?.run {
            this.statusBarState = statusBarState
            this.statusBarColor = statusBarColor
        }
        mWebViewModel.initStatusBar(this, mWebViewConfigBean)
    }

    fun showToolbar(isShowToolbar: Boolean) {
        mWebViewToolbar?.get()?.visibility = if (isShowToolbar) View.VISIBLE else View.GONE
    }

    fun shouldOverrideUrlLoading(view: WebView, url: String) {
    }
}