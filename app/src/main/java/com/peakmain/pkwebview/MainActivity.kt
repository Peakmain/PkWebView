package com.peakmain.pkwebview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.peakmain.webview.activity.WebViewActivity
import com.peakmain.webview.bean.WebViewConfigBean
import com.peakmain.webview.helper.WebViewHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.tv_webview).setOnClickListener{
           val bean= WebViewConfigBean("https://qa-xbu-activity.at-our.com/mallIndex")
           val intent=Intent(this, WebViewActivity::class.java)
            intent.putExtra(WebViewHelper.LIBRARY_WEB_VIEW,bean)
            startActivity(intent)
        }
    }
}