package com.peakmain.webview

import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.TypedValue
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.appbar.AppBarLayout
import com.peakmain.basiclibrary.base.activity.BaseActivity
import com.peakmain.basiclibrary.utils.StatusBarUtils
import com.peakmain.ui.navigationbar.DefaultNavigationBar
import com.peakmain.webview.bean.WebViewConfigBean
import com.peakmain.webview.databinding.LayoutActivityWebViewBinding
import com.peakmain.webview.fragment.WebViewFragment
import com.peakmain.webview.helper.WebViewHelper
import com.peakmain.webview.viewmodel.WebViewModel

/**
 * author ：Peakmain
 * createTime：2023/4/1
 * mail:2726449200@qq.com
 * describe：
 */
internal class WebViewActivity(override val layoutId: Int = R.layout.layout_activity_web_view) :
    BaseActivity<LayoutActivityWebViewBinding, WebViewModel>() {

    private val mWebViewConfigBean by lazy {
        intent.extras?.getSerializable(WebViewHelper.LIBRARY_WEB_VIEW) as WebViewConfigBean
    }
    var mWebViewFragment: WebViewFragment? = null
    override fun initView() {
        val bundle = Bundle()
        if (!TextUtils.isEmpty(mWebViewConfigBean.url)) {
            bundle.putString(WebViewHelper.LIBRARY_WEB_VIEW_URL, mWebViewConfigBean.url)
        }
        if (mWebViewConfigBean.model == WebViewConfig.MODE_LIGHT) {
            StatusBarUtils.setLightMode(this)
        } else {
            StatusBarUtils.setDarkMode(this)
        }
        if (mWebViewConfigBean.statusBarColor != null) {
            StatusBarUtils.setColor(
                this,
                mWebViewConfigBean.statusBarColor!!,
                mWebViewConfigBean.statusBarAlpha
            )
        }
        mWebViewFragment = WebViewFragment()
        mWebViewFragment!!.arguments = bundle
        supportFragmentManager.beginTransaction().add(R.id.root, mWebViewFragment!!)
            .commitAllowingStateLoss()
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val url = intent.getStringExtra(WebViewHelper.LIBRARY_WEB_VIEW_URL)
        if (mWebViewFragment != null && !TextUtils.isEmpty(url)) {
            mWebViewFragment!!.loadUrl(intent.getStringExtra(WebViewHelper.LIBRARY_WEB_VIEW_URL)!!)
        }
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
        mWebViewFragment?.clearWebView();
    }


    fun onReceivedTitle(title: String) {
    }
}