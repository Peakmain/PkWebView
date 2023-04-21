package com.peakmain.webview.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview_activity_base)
        setView()
        initView()
    }


    private fun setView() {
        val view =
            LayoutInflater.from(this).inflate(getLayoutId(), findViewById(R.id.layout_content))
        mIvBack = findViewById(R.id.web_view_back)
        mTvTitle = findViewById(R.id.web_view_title)
        mTvRightText = findViewById(R.id.web_view_menu_right)
    }

    abstract fun getLayoutId(): Int
    abstract fun initView()

}