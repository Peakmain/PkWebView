package com.peakmain.webview.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.peakmain.webview.R

/**
 * author ：Peakmain
 * createTime：2023/04/21
 * mail:2726449200@qq.com
 * describe：
 */
abstract class BaseWebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview_activity_base)
        setView()
        initView()
    }


    private fun setView() {
        val content =
            LayoutInflater.from(this).inflate(getLayoutId(), findViewById(R.id.layout_content))

    }

    abstract fun getLayoutId(): Int
    abstract fun initView()

}