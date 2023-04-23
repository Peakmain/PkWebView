package com.peakmain.webview.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.peakmain.webview.R

/**
 * author ：Peakmain
 * createTime：2023/04/21
 * mail:2726449200@qq.com
 * describe：
 */
abstract class BaseWebViewActivity : AppCompatActivity() {
    protected var mIvBack: ImageButton? = null
    protected var mTvTitle: TextView? = null
    protected var mTvRightText: TextView? = null
    protected var mWebViewToolbar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview_activity_base)
        setView()
        initView()
    }

    fun setOnClickListener(
        leftListener: ((View) -> Unit)? = null,
        rightListener: ((View) -> Unit)? = null
    ) {
        mIvBack?.setOnClickListener {
            leftListener?.invoke(it)
        }
        mTvRightText?.setOnClickListener{
            rightListener?.invoke(it)
        }
    }

    private fun setView() {
        LayoutInflater.from(this).inflate(getLayoutId(), findViewById(R.id.layout_content))
        mIvBack = findViewById(R.id.web_view_back)
        mTvTitle = findViewById(R.id.web_view_title)
        mTvRightText = findViewById(R.id.web_view_menu_right)
        mWebViewToolbar = findViewById(R.id.web_view_toolbar)
    }

    abstract fun getLayoutId(): Int
    abstract fun initView()

}