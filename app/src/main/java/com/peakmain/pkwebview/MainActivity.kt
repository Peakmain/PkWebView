package com.peakmain.pkwebview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.peakmain.pkwebview.intent.ReplaceH5ConfigDecorator
import com.peakmain.webview.H5Utils
import com.peakmain.webview.activity.WebViewActivity
import com.peakmain.webview.bean.WebViewConfigBean
import com.peakmain.webview.helper.WebViewHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*  findViewById<TextView>(R.id.tv_webview).setOnClickListener{
              H5Utils(ReplaceH5ConfigDecorator())
                  .startActivity(this,"https://qa-xbu-activity.at-our.com/mallIndex")
          }*/
        findViewById<TextView>(R.id.tv_webview).setOnClickListener {
            H5Utils()
                .startActivity(this, "https://qa-xbu-activity.at-our.com/mallIndex")
        }
    }
}