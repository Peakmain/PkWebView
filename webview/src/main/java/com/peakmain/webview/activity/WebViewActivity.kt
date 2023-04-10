package com.peakmain.webview.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.peakmain.webview.BuildConfig
import com.peakmain.webview.R
import com.peakmain.webview.bean.WebViewConfigBean
import com.peakmain.webview.constants.WebViewConstants
import com.peakmain.webview.fragment.WebViewFragment
import com.peakmain.webview.helper.WebViewHelper

/**
 * author ：Peakmain
 * createTime：2023/4/1
 * mail:2726449200@qq.com
 * describe：
 */
class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_web_view)
        initView()
    }

    private val mWebViewConfigBean by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            intent.extras?.getSerializable(
                WebViewConstants.LIBRARY_WEB_VIEW,
                WebViewConfigBean::class.java
            )
        else
            intent.extras?.getSerializable(WebViewConstants.LIBRARY_WEB_VIEW) as WebViewConfigBean
    }
    var mWebViewFragment: WebViewFragment? = null

    private fun initView() {
        val bundle = Bundle()
        if (!TextUtils.isEmpty(mWebViewConfigBean?.url)) {
            bundle.putString(WebViewConstants.LIBRARY_WEB_VIEW_URL, mWebViewConfigBean?.url)
        }
        mWebViewFragment = WebViewFragment()
        mWebViewFragment!!.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.root, mWebViewFragment!!)
            .commitAllowingStateLoss()
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val url = intent.getStringExtra(WebViewConstants.LIBRARY_WEB_VIEW_URL)
        if (mWebViewFragment != null && !TextUtils.isEmpty(url)) {
            mWebViewFragment!!.loadUrl(intent.getStringExtra(WebViewConstants.LIBRARY_WEB_VIEW_URL)!!)
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
    }


    fun onReceivedTitle(title: String) {
    }
}