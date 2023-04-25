package com.peakmain.webview.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.peakmain.webview.R
import java.lang.ref.WeakReference

/**
 * author ：Peakmain
 * createTime：2023/04/21
 * mail:2726449200@qq.com
 * describe：
 */
abstract class BaseWebViewActivity : AppCompatActivity() {
    protected var mIvBack: WeakReference<ImageButton>? = null
    protected var mTvTitle: WeakReference<TextView>? = null
    protected var mTvRightText: WeakReference<TextView>? = null
    protected var mWebViewToolbar: WeakReference<Toolbar>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview_activity_base)
        setView()
        initView()
    }

    fun clear() {
        mIvBack = null
        mTvRightText = null
        mTvTitle = null
        mWebViewToolbar = null
    }

    fun setToolbarStyle(block: ((Toolbar?, ImageButton?, TextView?, TextView?) -> Unit)? = null) {
        block?.invoke(mWebViewToolbar?.get(), mIvBack?.get(), mTvTitle?.get(), mTvRightText?.get())
    }

    fun setOnClickListener(
        leftListener: ((View) -> Unit)? = null,
        rightListener: ((View) -> Unit)? = null
    ) {
        mIvBack?.get()?.setOnClickListener {
            leftListener?.invoke(it)
        }
        mTvRightText?.get()?.setOnClickListener {
            rightListener?.invoke(it)
        }
    }

    private fun setView() {
        LayoutInflater.from(this).inflate(getLayoutId(), findViewById(R.id.layout_content))
        mIvBack = WeakReference(findViewById(R.id.web_view_back))
        mTvTitle = WeakReference(findViewById(R.id.web_view_title))
        mTvRightText = WeakReference(findViewById(R.id.web_view_menu_right))
        mWebViewToolbar = WeakReference(findViewById(R.id.web_view_toolbar))
    }

    abstract fun getLayoutId(): Int
    abstract fun initView()

}